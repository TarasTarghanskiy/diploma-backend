package server.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.StoryAccountDTO;
import server.DTO.StoryDTO;
import server.entity.*;
import server.repository.AccountRepository;
import server.repository.StoryAccountRepository;
import server.repository.StoryRepository;
import server.service.StoryService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class StoryServiceImpl implements StoryService {

    private StoryRepository storyRepository;
    private ObjectMapperUtils omu;
    private StoryAccountRepository storyAccountRepository;
    private AccountRepository accountRepository;
    private PaginationCountUtil pcu;

    public StoryServiceImpl(
            StoryRepository storyRepository,
            ObjectMapperUtils omu,
            StoryAccountRepository storyAccountRepository,
            AccountRepository accountRepository,
            PaginationCountUtil pcu
    ) {
        this.pcu = pcu;
        this.storyAccountRepository = storyAccountRepository;
        this.omu = omu;
        this.storyRepository = storyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void addStory(String story, String link, ServerEntity serverEntity) {
        StoryEntity storyEntity = new StoryEntity();
        storyEntity.setText(story);
        storyEntity.setLink(link);
        storyRepository.save(storyEntity);
        List<UserEntity> userEntityList = serverEntity.getUserServerList();
        for (UserEntity userEntity : userEntityList) {
            AccountEntity accountEntity = userEntity.getUserAccount();


            StoryAccountEntity storyAccountEntity = new StoryAccountEntity(storyEntity, accountEntity);
            storyAccountEntity.setViewed(false);
            storyAccountRepository.save(storyAccountEntity);


            storyEntity.getAccountSet().add(storyAccountEntity);
            accountEntity.getStorySet().add(storyAccountEntity);
            accountRepository.save(accountEntity);
            storyRepository.save(storyEntity);
        }
    }

    @Override
    public void addStory(String story, String link, AccountEntity accountEntity) {
        StoryEntity storyEntity = new StoryEntity();
        storyEntity.setText(story);
        storyEntity.setLink(link);
        storyRepository.save(storyEntity);


        StoryAccountEntity storyAccountEntity = new StoryAccountEntity(storyEntity, accountEntity);
        storyAccountEntity.setViewed(false);
        storyAccountRepository.save(storyAccountEntity);


        storyEntity.getAccountSet().add(storyAccountEntity);
        accountEntity.getStorySet().add(storyAccountEntity);
        accountRepository.save(accountEntity);
        storyRepository.save(storyEntity);
    }

    @Override
    public List<StoryDTO> getStoryList(int page) {
        return omu.mapAll(storyRepository.findAll(PageRequest.of(page, 10, Sort.by("storyID").descending())).toList(), StoryDTO.class);
    }

    @Override
    public List<Integer> getPageCount(int page) {
        return pcu.PageNumberList(page, storyRepository.countAllByStoryIDIsGreaterThan(0));
    }

    @Override
    public List<StoryAccountDTO> getStoryListByAcc(int page, String accountName) {
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        if (accountEntity == null || accountEntity.getStorySet() == null) return null;
        List<StoryAccountEntity> d = storyAccountRepository.findAllByAccount(accountEntity, PageRequest.of(page, 10, Sort.by("story", "viewed"))).toList();
        List<StoryAccountDTO> list = omu.mapAll(d, StoryAccountDTO.class);
        for (StoryAccountEntity storyAccountEntity : d) {
            storyAccountEntity.setViewed(true);
            storyAccountRepository.save(storyAccountEntity);
        }
        return list.stream().sorted(this::storyAccountSort).collect(Collectors.toList());
    }

    @Override
    public Integer getNewsCount(String accountName) {
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        return storyAccountRepository.countAllByAccountAndViewed(accountEntity, false);
    }

    int storyAccountSort(StoryAccountDTO saa1, StoryAccountDTO saa2) {
        if ((saa1.isViewed() && saa2.isViewed()) || (!saa1.isViewed() && !saa2.isViewed())) {
            return saa1.getStory().getCreationDate().compareTo(saa2.getStory().getCreationDate());
        } else if (saa1.isViewed()) {
            return -1;
        } else {
            return 1;
        }
    }
}

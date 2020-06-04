package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.ServerDTO;
import server.DTO.UserDTO;
import server.entity.AccountEntity;
import server.entity.ServerEntity;
import server.entity.UserEntity;
import server.repository.AccountRepository;
import server.repository.ServerRepository;
import server.repository.UserRepository;
import server.service.ServerService;
import server.service.StoryService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private ServerRepository serverRepository;

    private AccountRepository accountRepository;

    private UserRepository userRepository;

    private ObjectMapperUtils omu;

    private PaginationCountUtil pcu;

    private StoryService storyService;

    public ServerServiceImpl(ServerRepository serverRepository, AccountRepository accountRepository, UserRepository userRepository, ObjectMapperUtils omu, PaginationCountUtil pcu, StoryService storyService) {
        this.serverRepository = serverRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.omu = omu;
        this.pcu = pcu;
        this.storyService = storyService;
    }

    @Override
    public List<ServerDTO> getServerList(int page) {
        return omu.mapAll(serverRepository.findAll(PageRequest.of(page, 10)).toList(), ServerDTO.class);
    }

    @Override
    public List<Integer> getPageCount(int page) {
        return pcu.PageNumberList(page, serverRepository.countAllByServerIDIsGreaterThan(0));
    }

    @Override
    public List<String> create(ServerDTO serverDTO, String accountName) {
        System.out.println(serverDTO.getName());
        // ERRORS

        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        ServerEntity serverEntity = omu.map(serverDTO, ServerEntity.class);
        System.out.println(serverEntity.getName());
        accountEntity.getServerAccountList().add(serverEntity);
        serverEntity.setServerAccount(accountEntity);
        serverRepository.save(serverEntity);
        accountRepository.save(accountEntity);
        storyService.addStory("New server " + serverEntity.getName() + " was created","/server/"+serverEntity.getServerID(), accountEntity);
        return null;
    }

    @Override
    public List<String> createUser(UserDTO userDTO, String accountName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        ServerEntity serverEntity = serverRepository.getOne(userDTO.getServerID());
        userEntity.setReputation(100);
        userEntity.setStatus("active");

        accountEntity.getAccountUserList().add(userEntity);
        userEntity.setUserAccount(accountEntity);

        serverEntity.getUserServerList().add(userEntity);
        userEntity.setUserServer(serverEntity);

        userRepository.save(userEntity);
        accountRepository.save(accountEntity);
        serverRepository.save(serverEntity);

        storyService.addStory("Welcome! New user " + userEntity.getUserName() + " join us by server " + serverEntity.getName(),"/server/"+serverEntity.getServerID(), accountEntity);
        return null;
    }

    @Override
    public UserDTO getUserDTO(long serverID, String accountName) {
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        ServerEntity serverEntity = serverRepository.getOne(serverID);
        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accountEntity);
        if (userEntity == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setUserID(userEntity.getUserID());
        return userDTO;
    }

    @Override
    public long getWholeReputationCount() {
        long count = 0;
        for (UserEntity userEntity : userRepository.findAll()) {
            count += userEntity.getReputation();
        }
        return count;
    }
}

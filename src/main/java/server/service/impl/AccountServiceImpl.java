package server.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.AccountDTO;
import server.entity.AccountEntity;
import server.repository.AccountRepository;
import server.service.AccountService;
import server.service.StoryService;
import server.util.ObjectMapperUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static server.util.Constant.START_AVATAR_PATH;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {


    private ObjectMapperUtils omu;
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bcpe;
    private StoryService storyService;

    public AccountServiceImpl(
            ObjectMapperUtils omu,
            AccountRepository accountRepository,
            BCryptPasswordEncoder bcpe,
            StoryService storyService
    ) {
        this.storyService = storyService;
        this.omu = omu;
        this.accountRepository = accountRepository;
        this.bcpe = bcpe;
    }

    @Override
    public AccountDTO getAccount(String accountName) {
        AccountDTO accountDTO = omu.map(accountRepository.getAccountEntityByAccountName(accountName), AccountDTO.class);
        return accountDTO;
    }

    @Override
    public List<String> addAccount(AccountDTO accountDTO) throws IOException {
        List<String> errorList = new ArrayList<>();
        if (accountRepository.existsAccountEntitiesByAccountName(accountDTO.getAccountName())) {
            errorList.add("Account with name " + accountDTO.getAccountName() + " already exist");
        }

        if (accountDTO.getAccountName().length() > 20) {
            errorList.add("Account name can't be longer than 20 symbols");
        }

        if (accountDTO.getAccountName().length() < 5) {
            errorList.add("Account name must be longer than 5 symbols");
        }

        if (accountDTO.getAccountPassword() == null || accountDTO.getAccountPassword().length() < 6) {
            errorList.add("Account password must be longer than 6 symbols");
        }

        if (errorList.size() == 0) {
            accountDTO.setAccountPassword(bcpe.encode(accountDTO.getAccountPassword()));
            AccountEntity accountEntity = omu.map(accountDTO, AccountEntity.class);
            accountEntity.setAccountAvatar(IOUtils.toByteArray(getClass().getResourceAsStream("/startAvatar.jpg")));
            accountRepository.save(accountEntity);


            storyService.addStory("New account " + accountDTO.getAccountName() + " was created","/acc/"+accountEntity.getAccountID(), accountEntity);
            return null;
        } else return errorList;
    }

    @Override
    public byte[] getAccountAvatar(long accountID) {
        return omu.map(accountRepository.getAccountEntityByAccountID(accountID), AccountDTO.class).getAccountAvatar();
    }


}

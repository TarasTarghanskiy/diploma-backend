package server.service;

import server.DTO.AccountDTO;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    AccountDTO getAccount(String accountName);

    List<String> addAccount(AccountDTO accountDTO) throws IOException;

    byte[] getAccountAvatar(long accountID);
}

package server.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.DTO.AccountDTO;
import server.entity.UserEntity;
import server.repository.UserRepository;
import server.service.AccountService;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AccountDTO accountDTO = accountService.getAccount(userName);

        if (accountDTO == null) throw new UsernameNotFoundException(userName);

        return new User(accountDTO.getAccountName(), accountDTO.getAccountPassword(), Collections.emptyList());
    }


}

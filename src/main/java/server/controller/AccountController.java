package server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.AccountDTO;
import server.service.AccountService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/acc")
@CrossOrigin
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<String>> createAccount(@RequestBody AccountDTO accountDTO) throws IOException {
        List<String> errorList = accountService.addAccount(accountDTO);
        if (errorList == null)
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(errorList, HttpStatus.NOT_ACCEPTABLE);
    }


    @GetMapping("/avatar/{account_id}")
    public ResponseEntity<byte[]> accountAvatar(@PathVariable("account_id") long accountID) {
        return new ResponseEntity<>(accountService.getAccountAvatar(accountID), HttpStatus.OK);
    }

    @GetMapping("/id") // for what?
    public ResponseEntity<AccountDTO> accountID(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountID(accountService.getAccount(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).getAccountID());
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }


    @GetMapping("/data")
    public ResponseEntity<AccountDTO> getUserName() {
        AccountDTO accountDTO = accountService.getAccount(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        accountDTO.setAccountPassword(null);
        accountDTO.setAccountAvatar(null);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}

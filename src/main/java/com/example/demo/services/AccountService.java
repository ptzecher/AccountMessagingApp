package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String createAccount(String username) {
        if(!username.matches("[a-zA-Z]+")){
            return "Invalid username";
        }
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if(existingAccount.isPresent()){

           return "Account already exists";
        }
        Account account = new Account(username);
        accountRepository.save(account);
        return account.getAuthToken().toString();

    }

    public String deleteAccount(Long authToken) {
        Optional<Account> existingAccount = accountRepository.findByAuthToken(authToken);
        if(existingAccount.isPresent()){
            accountRepository.delete(existingAccount.get());
            return "Account successfully deleted with id "+authToken;
        }
        return "Account does't exist";
    }


}

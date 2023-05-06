package com.example.backend.factory;

import com.example.backend.model.Account;
import com.example.backend.model.DTO.AccountDTO;

public class AccountFactory {
    public AccountDTO mapperToDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountID(account.getAccountID());
        accountDTO.setUserName(account.getUsername());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setRole(String.valueOf(account.getRole()));
        return accountDTO;
    }
}

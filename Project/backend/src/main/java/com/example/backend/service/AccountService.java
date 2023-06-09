package com.example.backend.service;

import com.example.backend.model.Account;
import com.example.backend.model.DTO.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AccountDTO findById(Long id);
    List<Account> getAll();
    Account save(Account account);
    AccountDTO update(AccountDTO account,Long id);
    String delete(Long id);
    Boolean existsByUserName(String userName);
    Boolean changePassword(Long id, String oldPassword, String newPassword);
    Boolean forgotPassword(String email);
    Page<Account> findAllAcc(Long accountID, Pageable pageable);
}

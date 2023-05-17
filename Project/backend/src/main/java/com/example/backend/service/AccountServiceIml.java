package com.example.backend.service;

import com.example.backend.exception.GlobalException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.factory.AccountFactory;
import com.example.backend.model.Account;
import com.example.backend.model.DTO.AccountDTO;
import com.example.backend.model.Role;
import com.example.backend.repository.AccountRepository;
import com.example.backend.sendmail.MyConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    AccountFactory accountFactory = new AccountFactory();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private OrderService orderService;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found the account with id = " + id));
        return accountFactory.mapperToDTO(account);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountDTO update(AccountDTO newAccount, Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setFullName(newAccount.getFullName());
                    account.setEmail(newAccount.getEmail());
                    account.setPhone(newAccount.getPhone());
                    account.setRole(Role.valueOf(newAccount.getRole()));
                    return accountFactory.mapperToDTO(accountRepository.save(account));
                }).orElseThrow(() -> new NotFoundException("Could not found the account with id = " + id));
    }

    @Override
    public String delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new NotFoundException("Could not found the account with id = " + id);
        }
        if(orderService.findByAccountId(id).size() != 0){
            throw new GlobalException("Existing order that cannot be deleted accouunt");
        }
        accountRepository.deleteById(id);
        return "Account with id = " + id + " has been deleted success!";
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return accountRepository.existsByUserName(userName);
    }

    @Override
    public Boolean changePassword(Long id, String oldPassword, String newPassword) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new GlobalException("Could not found Account with id = " + id));
        if(passwordEncoder.matches(oldPassword,account.getPassword())){
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean forgotPassword(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Could not found Account with enail = "+ email));
        SimpleMailMessage message = new SimpleMailMessage();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String pwd = RandomStringUtils.random( 8, characters );
        message.setTo(email);
        message.setSubject("Your New Password");
        message.setText(pwd);
        emailSender.send(message);
        account.setPassword(passwordEncoder.encode(pwd));
        accountRepository.save(account);
        return true;
    }

    @Override
    public Page<Account> findAllAcc(Long accountID, Pageable pageable) {
        return accountRepository.findAllAcc(accountID,pageable);
    }
}

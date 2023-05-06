package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Account;
import com.example.backend.model.Product;
import com.example.backend.model.View;
import com.example.backend.model.ViewKey;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ViewServiceIml implements ViewService {
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Boolean save(Long accountID, Long productID) {
        ViewKey viewKey = new ViewKey(accountID,productID);
        Product product = productRepository.findById(productID)
                .orElseThrow(()->new NotFoundException("Could not found Product with id = "+productID));
        Account account = accountRepository.findById(accountID)
                .orElseThrow(()->new NotFoundException("Could not found Account with id = "+accountID));
        View view = new View();
        view.setViewID(viewKey);
        view.setProduct(product);
        view.setAccount(account);
        Date date = new Date();
        view.setDate(date);
        viewRepository.save(view);
        return true;
    }
}

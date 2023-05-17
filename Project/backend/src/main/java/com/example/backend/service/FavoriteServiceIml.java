package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Account;
import com.example.backend.model.Favorite;
import com.example.backend.model.FavoriteKey;
import com.example.backend.model.Product;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.FavoriteRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceIml implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Long getFavorite(Long productID) {
        if(!productRepository.existsById(productID)){
            throw new NotFoundException("Could not found product with id = " + productID);
        }
        return favoriteRepository.getFavorite(productID);
    }

    @Override
    public Boolean getExists(Long accountID,Long productID) {
        FavoriteKey favoriteKey = new FavoriteKey(accountID,productID);
        return favoriteRepository.existsById(favoriteKey);
    }

    @Override
    public Boolean save(Long accountID,Long productID) {
        FavoriteKey favoriteKey = new FavoriteKey(accountID,productID);
        Product product = productRepository.findById(productID)
                .orElseThrow(()->new NotFoundException("Could not found Product with id = "+productID));
        Account account = accountRepository.findById(accountID)
                .orElseThrow(()->new NotFoundException("Could not found Account with id = "+accountID));
        Favorite favorite = new Favorite(favoriteKey,product,account);
        favoriteRepository.save(favorite);
        return true;
    }

    @Override
    public Boolean delete(Long accountID,Long productID) {
        FavoriteKey favoriteKey = new FavoriteKey(accountID,productID);
        if(!favoriteRepository.existsById(favoriteKey)){
            throw new NotFoundException("Could not found favorite");
        }
        favoriteRepository.deleteById(favoriteKey);
        return true;
    }

    @Override
    public List<Product> findTop5Product() {
        return favoriteRepository.findTop5Product();
    }
}

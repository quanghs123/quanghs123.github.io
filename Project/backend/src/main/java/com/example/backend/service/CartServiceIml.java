package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Account;
import com.example.backend.model.Cart;
import com.example.backend.model.CartKey;
import com.example.backend.model.DTO.CartDTO;
import com.example.backend.model.Product;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceIml implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Boolean save(CartDTO cartDTO) {
        CartKey cartKey = new CartKey(cartDTO.getAccountID(), cartDTO.getProductID());
        if (cartRepository.existsById(cartKey)) {
            Cart cart = cartRepository.findById(cartKey)
                    .orElseThrow(()->new NotFoundException("Could not found Cart Item with AccountID = "
                            + cartKey.getAccountID() + " and ProductID = " + cartKey.getProductID()));
            cart.setProductQuantity(cartDTO.getProductQuantity());
            cartRepository.save(cart);
        } else {
            Account account = accountRepository.findById(cartDTO.getAccountID())
                    .orElseThrow(() -> new NotFoundException("Could not found Account with id = " + cartDTO.getAccountID()));
            Product product = productRepository.findById(cartDTO.getProductID())
                    .orElseThrow(() -> new NotFoundException("Could not found Product with id = " + cartDTO.getProductID()));
            Cart cart = new Cart();
            cart.setCartID(cartKey);
            cart.setAccount(account);
            cart.setProduct(product);
            cart.setProductQuantity(cartDTO.getProductQuantity());
            cartRepository.save(cart);
        }
        return true;
    }

    @Override
    public List<Cart> findAll(Long accountID) {
        return cartRepository.findByAccountAccountID(accountID);
    }

    @Override
    public Boolean delete(CartKey cartKey) {
        if(!cartRepository.existsById(cartKey)){
            throw new NotFoundException("Could not found Cart Item");
        }
        cartRepository.deleteById(cartKey);
        return true;
    }

    @Override
    public Long deleteByAccountID(Long accountID) {
        if(!accountRepository.existsById(accountID)){
            throw new NotFoundException("Could not found Account With id = "+ accountID);
        }
        return cartRepository.deleteByCartIDAccountID(accountID);
    }
}

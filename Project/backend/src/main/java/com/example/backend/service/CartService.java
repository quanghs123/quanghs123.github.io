package com.example.backend.service;

import com.example.backend.model.Cart;
import com.example.backend.model.CartKey;
import com.example.backend.model.DTO.CartDTO;

import java.util.List;

public interface CartService {
    Boolean save(CartDTO cartDTO);
    List<Cart> findAll(Long accountID);
    Boolean delete(CartKey cartKey);
    Long deleteByAccountID(Long accountID);
}

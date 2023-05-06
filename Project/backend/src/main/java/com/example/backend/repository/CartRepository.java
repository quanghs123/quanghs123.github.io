package com.example.backend.repository;

import com.example.backend.model.Cart;
import com.example.backend.model.CartKey;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartKey> {
    List<Cart> findByAccountAccountID(Long accountID);
    Long deleteByCartIDAccountID(Long accountID);
}

package com.example.backend.service;

import com.example.backend.model.Account;
import com.example.backend.model.Order;
import com.example.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order findById(Long id);

    Order save(Account account);

    Order update(int status,Long id);
    Boolean delete(Long id);
    Order findByAccountFullNameLike(String fullName);
    List<Order> findByAccountId(Long id);
    List<Order> findByAccountId1(Long id);
    Page<Order> findAllOr(Pageable pageable);
}

package com.example.backend.service;

import com.example.backend.model.Account;
import com.example.backend.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order findById(Long id);

    Order save(Account account);

    Order update(Order order,Long id);

    Boolean delete(Long id);
    Order findByAccountFullNameLike(String fullName);
    List<Order> findByAccountId(Long id);
    List<Order> findByAccountId1(Long id);
}

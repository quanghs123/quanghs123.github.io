package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Account;
import com.example.backend.model.Order;
import com.example.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class OrderServiceIml implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Could not found the order with id = " + id));
    }

    @Override
    public Order save(Account account) {
        Order order = new Order();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        order.setDate(date);
        order.setStatus(0);
        order.setAccount(account);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order newOrder, Long id) {
        return orderRepository.findById(id)
                .map(order->{
                    order.setStatus(newOrder.getStatus());
                    return orderRepository.save(order);
                }).orElseThrow(() -> new NotFoundException("Could not found the order with id = " + id));
    }

    @Override
    public Boolean delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException("Could not found the order with id = " + id);
        }
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Order findByAccountFullNameLike(String fullName) {
        return orderRepository.findByAccountFullNameLike(fullName);
    }

    @Override
    public List<Order> findByAccountId(Long id) {
        return orderRepository.findByAccountID(id);
    }
    @Override
    public List<Order> findByAccountId1(Long id) {
        return orderRepository.findByAccountID1(id);
    }

}

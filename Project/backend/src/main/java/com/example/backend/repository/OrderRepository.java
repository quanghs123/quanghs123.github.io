package com.example.backend.repository;

import com.example.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByAccountFullNameLike(String fullName);
    @Query(value = "SELECT o FROM Order o WHERE o.account.accountID = :accountID AND o.status = 0")
    List<Order> findByAccountID(@Param("accountID") Long accountID);
    @Query(value = "SELECT o FROM Order o WHERE o.account.accountID = :accountID AND o.status = 1")
    List<Order> findByAccountID1(@Param("accountID") Long accountID);
}

package com.example.backend.repository;

import com.example.backend.model.OrderDetail;
import com.example.backend.model.OrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {
    @Query(value = "SELECT od FROM OrderDetail od WHERE od.orderDetailID.orderID = :orderID")
    List<OrderDetail> findOrderDetailByOrderId(@Param("orderID") Long orderID);

    Long deleteByOrderOrderID(@Param("orderID") Long orderID);
}

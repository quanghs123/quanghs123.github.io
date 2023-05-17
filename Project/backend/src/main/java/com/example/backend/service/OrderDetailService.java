package com.example.backend.service;

import com.example.backend.model.DTO.OrderDetailDTO;
import com.example.backend.model.OrderDetail;
import com.example.backend.model.Product;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findOrderDetailByOrderId(Long orderID);
    OrderDetail save(OrderDetailDTO orderDetailDto);
    Long deleteOrderDetailByOrderID(Long orderID);
    List<Product> findTop5Product();
    List<Double> revenueYear();
}

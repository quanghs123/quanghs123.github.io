package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.DTO.OrderDetailDTO;
import com.example.backend.model.Order;
import com.example.backend.model.OrderDetail;
import com.example.backend.model.OrderDetailKey;
import com.example.backend.model.Product;
import com.example.backend.repository.OrderDetailRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceIml implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Long orderID) {
        return orderDetailRepository.findOrderDetailByOrderId(orderID);
    }

    @Override
    public OrderDetail save(OrderDetailDTO orderDetailDto) {
        Order order = orderRepository.findById(orderDetailDto.getOrderID())
                .orElseThrow(() -> new NotFoundException("Could not found Order with id = " + orderDetailDto.getOrderID()));
        Product product = productRepository.findById(orderDetailDto.getProductID())
                .orElseThrow(() -> new NotFoundException("Could not found Product with id = " + orderDetailDto.getProductID()));
        OrderDetailKey orderDetailKey = new OrderDetailKey(orderDetailDto.getOrderID(), orderDetailDto.getProductID());
        OrderDetail orderDetail = new OrderDetail(orderDetailKey, orderDetailDto.getProductQuantity(), order, product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public Long deleteOrderDetailByOrderID(Long orderID) {
        if (!orderRepository.existsById(orderID)) {
            throw new NotFoundException("Could not found Order with id = " + orderID);
        }
        List<OrderDetail> list = orderDetailRepository.findByOrderOrderID(orderID);
        for (OrderDetail orderDetail : list) {
            Product product = productRepository.findById(orderDetail.getOrderDetailID().getProductID()).
                    orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + orderDetail.getOrderDetailID().getProductID()));
            product.setProductQuantity(product.getProductQuantity() + orderDetail.getOrderQuantity());
            productRepository.save(product);
        }
        return orderDetailRepository.deleteByOrderOrderID(orderID);
    }

    @Override
    public List<Product> findTop5Product() {
        return orderDetailRepository.findTop5Product();
    }

    @Override
    public List<Double> revenueYear() {
        List<Double> list = new ArrayList<>();
        for(int i=1;i<=12;i++){
            if(orderDetailRepository.revenueMonth(i) == null){
                list.add(0d);
            }else{
                list.add(orderDetailRepository.revenueMonth(i));
            }
        }
        return list;
    }
}

package com.example.backend.controller;

import com.example.backend.model.DTO.OrderDetailDTO;
import com.example.backend.service.OrderDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/orderdetail")
@CrossOrigin
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/getbyorderid/{id}")
    ResponseEntity<?> getByOrderId(@PathVariable(value = "id") Long id){
        try {
            return ResponseEntity.ok(orderDetailService.findOrderDetailByOrderId(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }

    @PostMapping("/addorderdetail")
    ResponseEntity<?> save(@RequestBody OrderDetailDTO orderDetailDto){
        try {
            return ResponseEntity.ok(orderDetailService.save(orderDetailDto));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @Transactional
    @DeleteMapping("/deleteorderdetail/{orderID}")
    ResponseEntity<?> deleteOrderDetailByOrderID(@PathVariable(value = "orderID") Long orderID){
        return ResponseEntity.ok(orderDetailService.deleteOrderDetailByOrderID(orderID));
    }
}

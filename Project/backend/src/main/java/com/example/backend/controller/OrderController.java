package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.model.Brand;
import com.example.backend.model.Order;
import com.example.backend.model.Product;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/getall")
    ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok(orderService.getAll());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @GetMapping("/getbyid/{id}")
    ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(orderService.findById(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @PostMapping("/addorder")
    ResponseEntity<?> save(@RequestBody Account account){
        try {
            return ResponseEntity.ok(orderService.save(account));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @PutMapping("/editorder/{id}")
    ResponseEntity<?> update(@PathVariable(value = "id") Long id,
                             @RequestParam(value = "status") int status){
        try {
            return ResponseEntity.ok(orderService.update(status,id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @DeleteMapping("/deleteorder/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        try {
            return ResponseEntity.ok(orderService.delete(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }
    @GetMapping("/getbyaccountid/{id}")
    ResponseEntity<?> getByAccountId(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(orderService.findByAccountId(id));
    }
    @GetMapping("/getbyaccountid1/{id}")
    ResponseEntity<?> getByAccountId1(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(orderService.findByAccountId1(id));
    }
    @GetMapping("/getAll/findAllOr")
    public ResponseEntity<?> findAllOr(@RequestParam("offset") int offset,
                                       @RequestParam("pageSize") int pageSize) {
        Page<Order> orders = orderService.findAllOr(PageRequest.of(offset, pageSize));
        return ResponseEntity.ok(orders);

    }
}

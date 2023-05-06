package com.example.backend.controller;

import com.example.backend.model.CartKey;
import com.example.backend.model.DTO.CartDTO;
import com.example.backend.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("/getall")
    ResponseEntity<?> getAll(@RequestParam(value = "accountID") Long accountID){
        return ResponseEntity.ok(cartService.findAll(accountID));
    }
    @PostMapping("/addcart")
    ResponseEntity<?> save(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok(cartService.save(cartDTO));
    }
    @DeleteMapping("/delete")
    ResponseEntity<?> delete(@RequestBody CartKey cartKey){
        return ResponseEntity.ok(cartService.delete(cartKey));
    }

    @Transactional
    @DeleteMapping("/deletebyaccountid/{accountID}")
    ResponseEntity<?> deleteByAccountID(@PathVariable(value = "accountID") Long accountID){
        return ResponseEntity.ok(cartService.deleteByAccountID(accountID));
    }
}

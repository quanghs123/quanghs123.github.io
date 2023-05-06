package com.example.backend.controller;

import com.example.backend.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/view")
@CrossOrigin
public class ViewController {
    @Autowired
    private ViewService viewService;

    @PostMapping("/addview")
    ResponseEntity<?> save(@RequestParam(value = "accountID") Long accountID,
                           @RequestParam(value = "productID") Long productID){
        return ResponseEntity.ok(viewService.save(accountID,productID));
    }
}

package com.example.backend.controller;

import com.example.backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorite")
@CrossOrigin
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/getfavoritebyproductid/{id}")
    ResponseEntity<?> getFavoriteByProductid(@PathVariable(value = "id") Long productID){
        return ResponseEntity.ok(favoriteService.getFavorite(productID));
    }
    @GetMapping("/getfavoritebyid")
    ResponseEntity<?> getFavoriteById(@RequestParam(value = "accountID") Long accountID,
                                      @RequestParam(value = "productID") Long productID){
        return ResponseEntity.ok(favoriteService.getExists(accountID,productID));
    }

    @GetMapping("/findTop5Product")
    ResponseEntity<?> findTop5Product(){
        return ResponseEntity.ok(favoriteService.findTop5Product());
    }
    @PostMapping("/addfavorite")
    ResponseEntity<?> save(@RequestParam(value = "accountID") Long accountID,
                           @RequestParam(value = "productID") Long productID){
        return ResponseEntity.ok(favoriteService.save(accountID,productID));
    }
    @DeleteMapping("/deletefavorite")
    ResponseEntity<?> delete(@RequestParam(value = "accountID") Long accountID,
                             @RequestParam(value = "productID") Long productID){
        return ResponseEntity.ok(favoriteService.delete(accountID,productID));
    }
}

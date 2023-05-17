package com.example.backend.service;

import com.example.backend.model.Favorite;
import com.example.backend.model.FavoriteKey;
import com.example.backend.model.Product;

import java.util.List;

public interface FavoriteService {
    Long getFavorite(Long productID);
    Boolean getExists(Long accountID,Long productID);
    Boolean save(Long accountID,Long productID);
    Boolean delete(Long accountID,Long productID);
    List<Product> findTop5Product();
}

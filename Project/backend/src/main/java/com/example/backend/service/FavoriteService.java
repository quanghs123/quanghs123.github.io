package com.example.backend.service;

import com.example.backend.model.Favorite;
import com.example.backend.model.FavoriteKey;

public interface FavoriteService {
    Long getFavorite(Long productID);
    Boolean getExists(Long accountID,Long productID);
    Boolean save(Long accountID,Long productID);
    Boolean delete(Long accountID,Long productID);
}

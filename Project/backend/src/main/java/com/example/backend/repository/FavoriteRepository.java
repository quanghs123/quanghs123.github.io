package com.example.backend.repository;

import com.example.backend.model.Favorite;
import com.example.backend.model.FavoriteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteKey> {
    @Query(value = "SELECT COUNT(*) FROM project.favorite f WHERE f.productid = :productID",nativeQuery = true)
    Long getFavorite(@Param(value = "productID") Long productID);
}

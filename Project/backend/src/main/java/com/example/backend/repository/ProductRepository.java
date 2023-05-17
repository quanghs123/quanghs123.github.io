package com.example.backend.repository;

import com.example.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductName(String productName);
    List<Product> findByBrandBrandID(Long id);
    @Query(value = "SELECT p FROM Product p WHERE p.productID " +
            "IN (SELECT f.favoriteID.productID FROM Favorite f WHERE f.favoriteID.accountID = :accountID)")
    List<Product> findAllProductFavorite(@Param(value = "accountID") Long accountID);
    @Query(value = "SELECT v.product FROM View v WHERE v.viewID.accountID = :accountID ORDER BY v.date LIMIT 10")
    List<Product> findAllProductHistory(@Param(value = "accountID") Long accountID);
    @Query(value = "SELECT p FROM Product p WHERE p.productName LIKE %:productName%")
    List<Product> searchProductWithoutPrice(@Param("productName") String productName);
    @Query(value = "SELECT p FROM Product p WHERE p.productName LIKE %:productName% AND p.price BETWEEN :priceFrom AND :priceTo")
    List<Product> searchProductWithPrice(@Param("productName") String productName,
                                         @Param("priceFrom") Float priceFrom,
                                         @Param("priceTo") Float priceTo);
    @Query(value = "SELECT p FROM Product p")
    Page<Product> findAllPr(Pageable pageable);
}

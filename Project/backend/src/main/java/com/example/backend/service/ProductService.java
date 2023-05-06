package com.example.backend.service;

import com.example.backend.model.DTO.ProductDTO;
import com.example.backend.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    ProductDTO findById(Long id);
    List<Product> findByBrandBrandId(Long id);
    Product findByProductName(String productName);
    Product save(ProductDTO productDTO);
    Product update(ProductDTO productDTO,Long id);
    String delete(Long id);
    List<Product> findAllProductFavorite(Long accountID);
    List<Product> findAllProductHistory(Long accountID);
    List<Product> searchProductWithPrice(String productName,Float priceFrom, Float priceTo);
    List<Product> searchProductWithoutPrice(String productName);
}

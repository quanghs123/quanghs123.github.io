package com.example.backend.factory;

import com.example.backend.model.DTO.ProductDTO;
import com.example.backend.model.Product;

public class ProductFactory {
    public ProductDTO mapperFromEntityToDTO(Product product){
        return new ProductDTO(product);
    }
}

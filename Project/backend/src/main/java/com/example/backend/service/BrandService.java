package com.example.backend.service;

import com.example.backend.model.Brand;
import com.example.backend.model.DTO.BrandDTO;
import com.example.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();
    Brand findById(Long id);
    Brand save(BrandDTO brandDTO);
    String delete(Long id);
    Brand update(BrandDTO brandDTO,Long id);
    Page<Brand> findAllBr(Pageable pageable);
}

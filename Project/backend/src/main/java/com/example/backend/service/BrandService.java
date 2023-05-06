package com.example.backend.service;

import com.example.backend.model.Brand;
import com.example.backend.model.DTO.BrandDTO;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();
    Brand findById(Long id);
    Brand save(BrandDTO brandDTO);
    String delete(Long id);
    Brand update(BrandDTO brandDTO,Long id);
}

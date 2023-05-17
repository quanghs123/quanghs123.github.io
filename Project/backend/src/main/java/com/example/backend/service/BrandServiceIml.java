package com.example.backend.service;

import com.example.backend.exception.GlobalException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Brand;
import com.example.backend.model.DTO.BrandDTO;
import com.example.backend.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class BrandServiceIml implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductService productService;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Could not found brand with id = "+ id));

    }

    @Override
    public Brand save(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        getImageName(brandDTO, brand);
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(BrandDTO brandDto, Long id) {
        return brandRepository.findById(id)
                .map(brand -> {
                    brand.setBrandName(brandDto.getBrandName());
                    if (brandDto.getBrandImage() != null) {
                        getImageName(brandDto, brand);
                    }
                    return brandRepository.save(brand);
                }).orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + id));
    }

    @Override
    public Page<Brand> findAllBr(Pageable pageable) {
        return brandRepository.findAllBr(pageable);
    }

    @Override
    public String delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new NotFoundException("Could not found the brand with id = " + id);
        }
        if(productService.findByBrandBrandId(id).size() != 0){
            throw new GlobalException("Existing products that cannot be deleted brand");
        }
        brandRepository.deleteById(id);
        return "Brand with id = " + id + " has been deleted success!";
    }

    private static void getImageName(BrandDTO brandDTO, Brand brand) {
        MultipartFile image = brandDTO.getBrandImage();
        Path path = Paths.get("uploads/brand");
        if (image.isEmpty()) {
            brand.setBrandImage("default.jpg");
        }
        try {
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, path.resolve(image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            brand.setBrandImage(image.getOriginalFilename().toLowerCase());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

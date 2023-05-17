package com.example.backend.service;

import com.example.backend.exception.NotFoundException;
import com.example.backend.factory.ProductFactory;
import com.example.backend.model.Brand;
import com.example.backend.model.DTO.ProductDTO;
import com.example.backend.model.Product;
import com.example.backend.repository.BrandRepository;
import com.example.backend.repository.ProductRepository;
import jakarta.persistence.EntityManager;
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
import java.sql.Date;
import java.util.List;

@Service
public class ProductServiceIml implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    private ProductFactory productFactory = new ProductFactory();
    private EntityManager entityManager;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO findById(Long id) {
        return productFactory.mapperFromEntityToDTO(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + id)));
    }

    @Override
    public List<Product> findByBrandBrandId(Long id) {
        return productRepository.findByBrandBrandID(id);
    }

    @Override
    public Product findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        getImageName(productDTO, product);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        product.setDate(date);
        product.setPin(productDTO.getPin());
        product.setRam(productDTO.getRam());
        product.setScreenSize(productDTO.getScreenSize());
        product.setPrice(productDTO.getPrice());
        product.setOperatingSystem(productDTO.getOperatingSystem());
        product.setProductQuantity(productDTO.getProductQuantity());
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + productDTO.getBrandId()));
        product.setBrand(brand);
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDTO productDTO, Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDTO.getProductName());
                    product.setPin(productDTO.getPin());
                    product.setDate(productDTO.getDate());
                    Brand brand = brandRepository.findById(productDTO.getBrandId())
                            .orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + productDTO.getBrandId()));
                    product.setBrand(brand);
                    product.setPrice(productDTO.getPrice());
                    product.setRam(productDTO.getRam());
                    product.setScreenSize(productDTO.getScreenSize());
                    product.setOperatingSystem(productDTO.getOperatingSystem());
                    product.setProductQuantity(productDTO.getProductQuantity());
                    if (productDTO.getProductImage() != null) {
                        getImageName(productDTO, product);
                    }
                    return productRepository.save(product);
                }).orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + id));
    }

    @Override
    public String delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Could not product the brand with id = " + id);
        }
        productRepository.deleteById(id);
        return "Product with id = " + id + " has been deleted success!";
    }

    @Override
    public List<Product> findAllProductFavorite(Long accountID) {
        return productRepository.findAllProductFavorite(accountID);
    }

    @Override
    public List<Product> findAllProductHistory(Long accountID) {
        return productRepository.findAllProductHistory(accountID);
    }

    @Override
    public List<Product> searchProductWithPrice(String productName, Float priceFrom, Float priceTo) {
        return productRepository.searchProductWithPrice(productName, priceFrom, priceTo);
    }

    @Override
    public List<Product> searchProductWithoutPrice(String productName) {
        return productRepository.searchProductWithoutPrice(productName);
    }

    @Override
    public Boolean updateProductQuantity(Long productID, int quantity, Boolean flag) {
        Product product = productRepository.findById(productID).
                orElseThrow(() -> new NotFoundException("Could not found the brand with id = " + productID));
        if(flag){
            if(product.getProductQuantity() > quantity){
                product.setProductQuantity(product.getProductQuantity() - quantity);
            }else return false;
        }else{
            product.setProductQuantity(product.getProductQuantity() + quantity);
        }
        productRepository.save(product);
        return true;
    }

    @Override
    public Page<Product> findAllPr(Pageable pageable) {
        return productRepository.findAllPr(pageable);
    }

    private static void getImageName(ProductDTO productDTO, Product product) {
        MultipartFile image = productDTO.getProductImage();
        Path path = Paths.get("uploads/product");
        if (image.isEmpty()) {
            product.setProductImage("default.png");
        }
        try {
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, path.resolve(image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            product.setProductImage(image.getOriginalFilename().toLowerCase());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

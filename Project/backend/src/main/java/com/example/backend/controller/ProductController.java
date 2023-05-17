package com.example.backend.controller;


import com.example.backend.model.Account;
import com.example.backend.model.DTO.ProductDTO;
import com.example.backend.model.Product;
import com.example.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getall")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/searchProductWithPrice")
    ResponseEntity<?> searchProduct(@RequestParam(value = "productName") String productName,
                                    @RequestParam(value = "priceFrom") Float priceFrom,
                                    @RequestParam(value = "priceTo") Float priceTo) {
        return ResponseEntity.ok(productService.searchProductWithPrice(productName, priceFrom, priceTo));
    }

    @GetMapping("/searchProductWithoutPrice")
    ResponseEntity<?> searchProduct(@RequestParam(value = "productName") String productName) {
        return ResponseEntity.ok(productService.searchProductWithoutPrice(productName));
    }

    @GetMapping("/findByBrandID/{id}")
    ResponseEntity<?> findByBrandID(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.findByBrandBrandId(id));
    }

    @GetMapping("/findFavoriteByAccountID/{id}")
    ResponseEntity<?> findAllProductFavorite(@PathVariable(value = "id") Long accountID) {
        return ResponseEntity.ok(productService.findAllProductFavorite(accountID));
    }

    @GetMapping("/findHistoryByAccountID/{id}")
    ResponseEntity<?> findAllProductHistory(@PathVariable(value = "id") Long accountID) {
        return ResponseEntity.ok(productService.findAllProductHistory(accountID));
    }

    @GetMapping("/getbyid/{id}")
    ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping(value = "/addproduct",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> save(@ModelAttribute @Valid ProductDTO productDTO,
                           BindingResult bindingResult) {
        ResponseEntity<String> BAD_REQUEST = getValidateData(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        try {
            return ResponseEntity.ok(productService.save(productDTO));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    private static ResponseEntity<String> getValidateData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            String errorMsg = "";

            for (String key : errors.keySet()) {
                errorMsg += errors.get(key) + "\n";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    errorMsg
            );
        }
        return null;
    }

    @PutMapping("/editproduct/withimage/{id}")
    ResponseEntity<?> updateWithImage(@PathVariable(value = "id") Long id,
                                      @ModelAttribute @Valid ProductDTO productDTO,
                                      BindingResult bindingResult) {
        ResponseEntity<String> BAD_REQUEST = getValidateData(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        try {
            return ResponseEntity.ok(productService.update(productDTO, id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/editproduct/noimage/{id}")
    ResponseEntity<?> updateNoImage(@PathVariable(value = "id") Long id,
                                    @ModelAttribute @Valid ProductDTO productDTO,
                                    BindingResult bindingResult) {
        ResponseEntity<String> BAD_REQUEST = getValidateData(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        try {
            return ResponseEntity.ok(productService.update(productDTO, id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @DeleteMapping("/deleteproduct/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/updateproductquantity/{productID}")
    ResponseEntity<?> updateProductQuantity(@RequestParam(value = "quantity") int quantity,
                                            @RequestParam(value = "flag") Boolean flag,
                                            @PathVariable(value = "productID") Long productID) {
        return ResponseEntity.ok(productService.updateProductQuantity(productID, quantity, flag));
    }

    @GetMapping("/getAll/findAllPr")
    public ResponseEntity<?> findAllPr(@RequestParam("offset") int offset,
                                        @RequestParam("pageSize") int pageSize) {
        Page<Product> products = productService.findAllPr(PageRequest.of(offset, pageSize));
        return ResponseEntity.ok(products);

    }
}

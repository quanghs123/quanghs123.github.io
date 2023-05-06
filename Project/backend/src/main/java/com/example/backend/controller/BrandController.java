package com.example.backend.controller;

import com.example.backend.model.Brand;
import com.example.backend.model.DTO.BrandDTO;
import com.example.backend.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/getall")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }

    @GetMapping("/getbyid/{id}")
    ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(brandService.findById(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }

    @PostMapping(value = "/addbrand",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> save(@ModelAttribute @Valid BrandDTO brandDTO) {
        try {
            return ResponseEntity.ok(brandService.save(brandDTO));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }

    @PutMapping("/editbrand/withimage/{id}")
    ResponseEntity<?> updateWithImage(@PathVariable(value = "id") Long id,
                                      @ModelAttribute @Valid BrandDTO brandDTO) {

        try {
            return ResponseEntity.ok(brandService.update(brandDTO, id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }

    @PutMapping("/editbrand/noimage/{id}")
    ResponseEntity<?> updateNoImage(@PathVariable(value = "id") Long id,
                                    @ModelAttribute @Valid Brand brand) {
        try {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setBrandName(brand.getBrandName());
            return ResponseEntity.ok(brandService.update(brandDTO, id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),ex);
        }
    }

    @DeleteMapping("/deletebrand/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(brandService.delete(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(500).body(ex.getLocalizedMessage());
        }
    }
}

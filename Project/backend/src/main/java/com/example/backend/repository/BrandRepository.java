package com.example.backend.repository;

import com.example.backend.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query(value = "SELECT b FROM Brand b")
    Page<Brand> findAllBr(Pageable pageable);
}

package com.example.backend.repository;

import com.example.backend.model.View;
import com.example.backend.model.ViewKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, ViewKey> {
}

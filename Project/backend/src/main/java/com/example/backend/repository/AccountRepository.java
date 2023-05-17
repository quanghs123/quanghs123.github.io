package com.example.backend.repository;

import com.example.backend.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Optional<Account> findByEmail(String email);
    @Query(value = "SELECT a FROM Account a WHERE a.accountID != :accountID")
    Page<Account> findAllAcc(@Param("accountID") Long accountID, Pageable pageable);
}

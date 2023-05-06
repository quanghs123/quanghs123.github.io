package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.model.DTO.AccountDTO;
import com.example.backend.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/getbyid/{id}")
    ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(accountService.findById(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @GetMapping("/getall")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/existsByUserName")
    ResponseEntity<?> existsByUserName(@RequestParam String userName) {
        try {
            return ResponseEntity.ok(accountService.existsByUserName(userName));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/addaccount")
    ResponseEntity<?> save(@RequestBody @Valid Account account,
                           BindingResult bindingResult) {
        ResponseEntity<String> BAD_REQUEST = getValidateData(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        try {
            return ResponseEntity.ok(accountService.save(account));
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

    @PutMapping("/editaccount/{id}")
    ResponseEntity<?> update(@RequestBody @Valid AccountDTO account,
                             @PathVariable(value = "id") Long id,
                             BindingResult bindingResult) {
        ResponseEntity<String> BAD_REQUEST = getValidateData(bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        try {
            return ResponseEntity.ok(accountService.update(account, id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @DeleteMapping("/deleteaccount/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(accountService.delete(id));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/changeAccount/{id}")
    ResponseEntity<Boolean> changePassword(@PathVariable(value = "id") Long id,
                                           @RequestParam(value = "oldPassword") String oldPassword,
                                           @RequestParam(value = "newPassword") String newPassword) {
        try {
            return ResponseEntity.ok(accountService.changePassword(id, oldPassword, newPassword));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/forgotPassword")
    ResponseEntity<Boolean> forgotPassword(@RequestParam(value = "email") String email) {
        try {
            return ResponseEntity.ok(accountService.forgotPassword(email));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
        }
    }

}

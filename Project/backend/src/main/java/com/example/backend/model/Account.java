package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;
    @Column(name = "FullName")
    @Size(min = 3, max = 25, message = "Tên phải nằm trong 3 đến 25 ký tự")
    @NotBlank(message = "Tên không được đê trống")
    private String fullName;
    @Column(name = "UserName")
    @NotBlank(message = "Tài khoản không được đê trống")
    private String userName;
    @Column(name = "Password")
    @NotBlank(message = "password không được đê trống")
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password cần ít nhất 1 chữ hoa và 1 chữ thường và có độ dài ít nhất là 8")
    private String password;
    @Column(name = "Email")
    @Email(message = "Email không đúng")
    private String email;
    @Column(name = "Phone")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không đúng")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

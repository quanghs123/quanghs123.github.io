package com.example.backend.model.DTO;

import com.example.backend.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long accountID;
    @Column(name = "UserName")
    @NotBlank(message = "Tài khoản không được đê trống")
    private String userName;
    @Column(name = "FullName")
    @Size(min = 3, max = 25, message = "Tên phải nằm trong 3 đến 25 ký tự")
    @NotBlank(message = "Tên không được đê trống")
    private String fullName;
    @Column(name = "Email")
    @Email(message = "Email không đúng")
    private String email;
    @Column(name = "Phone")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không đúng")
    private String phone;
    private String role;
}

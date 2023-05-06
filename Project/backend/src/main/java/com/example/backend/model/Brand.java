package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandID;
    @Column(name = "BrandName")
    @NotBlank(message = "Ten thuong hieu khong duoc de trong")
    @Length(min = 3, max = 255,message = "Ten phai tu 3 den 255 ky tu")
    private String brandName;
    @Column(name = "BrandImage")
    private String brandImage;
}

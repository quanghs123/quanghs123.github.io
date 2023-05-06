package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    @Column(name = "ProductName")
    @NotBlank(message = "Ten thuong hieu khong duoc de trong")
    @Length(min = 3, max = 255,message = "Ten phai tu 3 den 255 ky tu")
    private String productName;
    @Column(name = "ProductPrice")
    @Min(value = 0, message = "Giá không được nhỏ hơn 0")
    private double price;

    @Column(name = "ProductRam")
    @Min(value = 0, message = "Ram không được nhỏ hơn 0")
    private double ram;

    @Column(name = "ProductScreenSize")
    @Min(value = 0, message = "Kich thuoc không được nhỏ hơn 0")
    private double screenSize;

    @Column(name = "ProductPin")
    @Min(value = 0, message = "Dung luong pin không được nhỏ hơn 0")
    private double pin;

    @Column(name = "ProductOperatingSystem")
    @NotBlank(message = "He dieu hanh khong duoc de trong")
    private String operatingSystem;

    @Column(name = "ProductImage")
    private String productImage;

    @Column(name = "ProductQuantity")
    private Long productQuantity;

    @Column(name = "Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "BrandID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Brand brand;

}

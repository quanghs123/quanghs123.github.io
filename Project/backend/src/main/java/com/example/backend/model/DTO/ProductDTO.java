package com.example.backend.model.DTO;

import com.example.backend.model.Brand;
import com.example.backend.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private Long productID;
    @NotBlank(message = "Ten thuong hieu khong duoc de trong")
    @Length(min = 3, max = 255, message = "Ten phai tu 3 den 255 ky tu")
    private String productName;
    @Min(value = 0, message = "Giá không được nhỏ hơn 0")
    private double price;
    @Min(value = 0, message = "Ram không được nhỏ hơn 0")
    private double ram;
    @Min(value = 0, message = "Kich thuoc không được nhỏ hơn 0")
    private double screenSize;
    @Min(value = 0, message = "Dung luong pin không được nhỏ hơn 0")
    private double pin;
    @NotBlank(message = "He dieu hanh khong duoc de trong")
    private String operatingSystem;
    private MultipartFile productImage;
    private Long brandId;
    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
    private Long productQuantity;
    private Date date;

    public ProductDTO(Product product) {
        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.ram = product.getRam();
        this.screenSize = product.getScreenSize();
        this.pin = product.getPin();
        this.operatingSystem = product.getOperatingSystem();
        this.brandId = product.getBrand().getBrandID();
        this.productQuantity = product.getProductQuantity();
    }
}

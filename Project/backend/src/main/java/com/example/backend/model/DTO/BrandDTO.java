package com.example.backend.model.DTO;

import com.example.backend.model.Brand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO implements Serializable{
    private Long brandID;
    @NotBlank(message = "Ten thuong hieu khong duoc de trong")
    @Length(min = 3, max = 255,message = "Ten phai tu 3 den 255 ky tu")
    private String brandName;
    private MultipartFile brandImage;
}

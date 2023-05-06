package com.example.myapplication.models;

import java.io.Serializable;

public class Brand implements Serializable {
    private Long brandID;
    private String brandName;
    private String brandImage;

    public Brand() {
    }

    public Brand(Long brandID, String brandName, String brandImage) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandImage = brandImage;
    }

    public Long getBrandID() {
        return brandID;
    }

    public void setBrandID(Long brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }
}

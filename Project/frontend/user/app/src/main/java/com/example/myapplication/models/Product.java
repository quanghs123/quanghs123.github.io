package com.example.myapplication.models;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private Long productID;
    private String productName;
    private double price;
    private double ram;
    private double screenSize;
    private double pin;
    private String operatingSystem;
    private String productImage;
    private Long productQuantity;
    private Date date;

    public Product() {
    }

    public Product(Long productID, String productName, double price, double ram, double screenSize, double pin, String operatingSystem, String productImage, Long productQuantity, Date date) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.ram = ram;
        this.screenSize = screenSize;
        this.pin = pin;
        this.operatingSystem = operatingSystem;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.date = date;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public double getPin() {
        return pin;
    }

    public void setPin(double pin) {
        this.pin = pin;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

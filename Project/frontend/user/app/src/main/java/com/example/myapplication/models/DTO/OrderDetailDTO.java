package com.example.myapplication.models.DTO;

public class OrderDetailDTO {
    private Long orderID;
    private Long productID;
    private int productQuantity;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long orderID, Long productID, int productQuantity) {
        this.orderID = orderID;
        this.productID = productID;
        this.productQuantity = productQuantity;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}

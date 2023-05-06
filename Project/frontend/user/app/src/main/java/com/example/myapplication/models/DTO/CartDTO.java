package com.example.myapplication.models.DTO;

public class CartDTO {
    private Long accountID;
    private Long productID;
    private int productQuantity;

    public CartDTO() {
    }

    public CartDTO(Long accountID, Long productID, int productQuantity) {
        this.accountID = accountID;
        this.productID = productID;
        this.productQuantity = productQuantity;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
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

package com.example.myapplication.models;

public class CartKey {
    private Long accountID;
    private Long productID;

    public CartKey() {
    }

    public CartKey(Long accountID, Long productID) {
        this.accountID = accountID;
        this.productID = productID;
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
}

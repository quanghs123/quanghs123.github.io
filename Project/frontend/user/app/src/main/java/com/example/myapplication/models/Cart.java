package com.example.myapplication.models;

public class Cart {
    private CartKey cartID;
    private int productQuantity;
    private Product product;
    private Account account;
    public Cart() {
    }

    public Cart(CartKey cartID, int productQuantity, Product product, Account account) {
        this.cartID = cartID;
        this.productQuantity = productQuantity;
        this.product = product;
        this.account = account;
    }

    public CartKey getCartID() {
        return cartID;
    }

    public void setCartID(CartKey cartID) {
        this.cartID = cartID;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

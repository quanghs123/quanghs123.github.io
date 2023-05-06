package com.example.myapplication.models;

public class Favorite {
    private FavoriteKey favoriteID;
    private Product product;
    private Account account;

    public Favorite() {
    }

    public Favorite(FavoriteKey favoriteID, Product product, Account account) {
        this.favoriteID = favoriteID;
        this.product = product;
        this.account = account;
    }

    public FavoriteKey getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(FavoriteKey favoriteID) {
        this.favoriteID = favoriteID;
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

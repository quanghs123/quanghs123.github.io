package com.example.myapplication.models;

import java.sql.Date;

public class Order {
    private Long orderID;
    private Date date;
    private int status;
    private Account account;

    public Order() {
    }

    public Order(Long orderID, Date date, int status, Account account) {
        this.orderID = orderID;
        this.date = date;
        this.status = status;
        this.account = account;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

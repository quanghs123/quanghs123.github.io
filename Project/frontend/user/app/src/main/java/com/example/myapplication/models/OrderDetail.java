package com.example.myapplication.models;

public class OrderDetail {
    private OrderDetailKey orderDetailID;
    private int orderQuantity;
    private Order order;
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailKey orderDetailID, int orderQuantity, Order order, Product product) {
        this.orderDetailID = orderDetailID;
        this.orderQuantity = orderQuantity;
        this.order = order;
        this.product = product;
    }

    public OrderDetailKey getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(OrderDetailKey orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

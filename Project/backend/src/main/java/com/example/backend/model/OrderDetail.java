package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OrderDetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @EmbeddedId
    private OrderDetailKey orderDetailID;
    @Column(name = "OrderQuantity")
    private int orderQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("OrderID")
    @JoinColumn(name = "OrderID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ProductID")
    @JoinColumn(name = "ProductID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;
}

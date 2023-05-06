package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @EmbeddedId
    private CartKey cartID;

    @Column(name = "ProductQuantity")
    private int productQuantity;

    @ManyToOne
    @MapsId("ProductID")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne
    @MapsId("AccountID")
    @JoinColumn(name = "AccountID")
    private Account account;
}

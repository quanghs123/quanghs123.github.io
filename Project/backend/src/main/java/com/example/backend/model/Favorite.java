package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Favorite")
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @EmbeddedId
    private FavoriteKey favoriteID;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ProductID")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("AccountID")
    @JoinColumn(name = "AccountID")
    private Account account;
}

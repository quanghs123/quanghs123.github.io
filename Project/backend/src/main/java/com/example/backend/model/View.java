package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "View")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class View {
    @EmbeddedId
    private ViewKey viewID;

    @Column(name = "Date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ProductID")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("AccountID")
    @JoinColumn(name = "AccountID")
    private Account account;
}

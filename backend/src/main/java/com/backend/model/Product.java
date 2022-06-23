package com.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private double price;

    private String name;

    private Product() {
    }

    public Product(double price, String name) {
        this.price = price;
        this.name = name;
    }
}

package com.backend.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private String username;

    private ShoppingBasket shoppingBasket;

    private double wallet;

    public UserResponse(Long id, String token, String username, ShoppingBasket shoppingBasket, double wallet) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.shoppingBasket = shoppingBasket;
        this.wallet = wallet;
    }
}
package com.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity @Data public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mode;

    private double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    private Payment() {
    }

    public Payment(String mode, double amount, Card card) {
        this.mode   = mode;
        this.amount = amount;
        this.card = card;
    }
}
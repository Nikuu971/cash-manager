package com.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String firstname;

    private String lastname;

    private String cardNumber;

    private String CVC;

    private Date date;

    public Card() {}
}

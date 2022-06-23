package com.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_basket_id", referencedColumnName = "id")
    private ShoppingBasket shoppingBasket;

    private double wallet;

    public UserApplication() {
        this.wallet = 100;
    }

    public UserApplication(String username) {
        this.username = username;
        this.shoppingBasket = new ShoppingBasket();
        this.wallet = 100;
    }

    public void AddShoppingBasketArticle(Product article) {
        this.shoppingBasket.AddArticle(article);
    }

    public void RemoveShoppingBasketArticle(Product article) {
        this.shoppingBasket.RemoveArticle(article);
    }

    public void ResetShoppingBasket() {
        this.shoppingBasket.ResetShoppingBasket();
    }
}

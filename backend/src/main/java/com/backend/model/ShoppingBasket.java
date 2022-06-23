package com.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "shopping_basket")
public class ShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> articles;

    private double total;

    public ShoppingBasket() {
        articles = new ArrayList<Product>();
    }

    public void AddArticle(Product article) {
        this.articles.add(article);
        this.total = this.total + article.getPrice();
    }

    public void RemoveArticle(Product article) {
        int index = articles.indexOf(article);

        if (index != -1) {
            this.articles.remove(index);
        }
    }

    public void ResetShoppingBasket() {
        this.articles.clear();
    }
}
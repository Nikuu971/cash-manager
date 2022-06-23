package com.backend.service.abstractions;

import com.backend.model.ShoppingBasket;

public interface IShoppingBasketService {
    ShoppingBasket getShoppingBasket(Long id);

    ShoppingBasket addArticle(Long id, Long article);

    ShoppingBasket removeArticle(Long id, Long article);
}
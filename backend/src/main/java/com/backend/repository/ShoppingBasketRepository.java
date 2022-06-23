package com.backend.repository;

import com.backend.model.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {
}

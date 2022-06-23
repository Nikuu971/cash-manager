package com.backend.service;

import com.backend.exception.UserException;
import com.backend.model.*;
import com.backend.repository.UserRepository;
import com.backend.service.abstractions.IProductService;
import com.backend.service.concretions.ShoppingBasketImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Class Shopping Basket Service Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class ShoppingBasketServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    IProductService productService;

    @InjectMocks
    ShoppingBasketImpl shoppingBasketService;

    /**
     * Test get shopping basket method
     * fail if shopping basket isn't correct
     */
    @Test
    public void getShoppingBasket() {
        final Long id = 1L;
        Product product = new Product(4.8, "product");
        UserApplication userApplication = new UserApplication("Jean");

        userApplication.AddShoppingBasketArticle(product);
        userApplication.setId(id);

        given(userRepository.findById(id)).willReturn(Optional.of(userApplication));

        ShoppingBasket expected = shoppingBasketService.getShoppingBasket(id);

        assertEquals(expected, userApplication.getShoppingBasket());
    }

    /**
     * Test get shopping basket method
     * fail if method doesn't throw
     */
    @Test(expected = UserException.class)
    public void getShoppingBasketError() {
        final Long id = 1L;

        shoppingBasketService.getShoppingBasket(id);
    }

    /**
     * Test add article method
     * fail if shopping basket isn't correct
     */
    @Test
    public void addArticle() {
        final Long    id        = 1L;
        final Long    id2       = 2L;
        UserApplication userApplication = new UserApplication("test");
        Product       product   = new Product(4.8, "produect");
        List<Product> data      = new ArrayList<Product>();

        data.add(product);
        userApplication.setId(id);
        product.setId(id2);

        given(userRepository.findById(id)).willReturn(Optional.of(userApplication));
        given(userRepository.save(userApplication)).willReturn(userApplication);
        given(productService.getProductById(id2)).willReturn(product);

        ShoppingBasket expected = shoppingBasketService.addArticle(id, id2);

        assertEquals(expected.getArticles(), data);
    }

    /**
     * Test add article method error
     * fail if method doesn't throw
     */
    @Test(expected = UserException.class)
    public void addArticleError() {
        final Long    id        = 1L;
        final Long    id2       = 2L;
        Product       product   = new Product(4.8, "produect");

        product.setId(id2);

        given(userRepository.findById(id)).willReturn(Optional.ofNullable(null));
        given(productService.getProductById(id2)).willReturn(product);

        shoppingBasketService.addArticle(id, id2);
    }

    /**
     * Test remove article method
     * fail if shopping basket isn't correct
     */
    @Test
    public void removeArticle() {
        final Long    id        = 1L;
        final Long    id2       = 2L;
        UserApplication userApplication = new UserApplication("test");
        Product       product   = new Product(4.8, "produect");
        List<Product> data      = new ArrayList<Product>();

        userApplication.setId(id);
        userApplication.AddShoppingBasketArticle(product);
        product.setId(id2);

        given(userRepository.findById(id)).willReturn(Optional.of(userApplication));
        given(userRepository.save(userApplication)).willReturn(userApplication);
        given(productService.getProductById(id2)).willReturn(product);

        ShoppingBasket expected = shoppingBasketService.removeArticle(id, id2);

        assertEquals(expected.getArticles(), data);
    }

    /**
     * Test remove article method error
     * fail if method doesn't throw
     */
    @Test(expected = UserException.class)
    public void removeArticleError() {
        final Long    id        = 1L;
        final Long    id2       = 2L;
        Product       product   = new Product(4.8, "produect");

        product.setId(id2);

        given(userRepository.findById(id)).willReturn(Optional.ofNullable(null));
        given(productService.getProductById(id2)).willReturn(product);

        shoppingBasketService.removeArticle(id, id2);
    }
}

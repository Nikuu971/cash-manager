package com.backend.controller;

import com.backend.exception.FindProductException;
import com.backend.exception.UserException;
import com.backend.model.ShoppingBasket;
import com.backend.service.abstractions.IShoppingBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class Rest API Shopping Basket Controller
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RestController
@RequestMapping(ShoppingBasketController.BASE_URL)
public class ShoppingBasketController {
    public  static final String BASE_URL = "/api/v1/users/{userId}/shoppingbaskets";

    private final IShoppingBasketService shoppingBasketService;

    /**
     * ShoppingBasket Controller constructor
     * Init Shopping Basket controller service
     * @param shoppingBasketService   Shopping Basket Service
     */
    @Autowired
    public ShoppingBasketController(IShoppingBasketService shoppingBasketService) {
        this.shoppingBasketService = shoppingBasketService;
    }

    /**
     * Get Shopping Basket of user by user Id
     *
     * @param userId User Id
     * @return user Shopping Basket if success or Bad Request if fail
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShoppingBasket(@PathVariable long userId) {
        try {
            ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(userId);
            return new ResponseEntity<>(shoppingBasket, HttpStatus.OK);
        }
        catch (UserException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add article to user Shopping Basket
     *
     * @param userId User Id
     * @param id Product Id
     * @return update user Shopping Basket if success or Bad Request if fail
     */
    @PostMapping("/add/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addArticle(@PathVariable long userId, @PathVariable long id)
    {
        try {
            ShoppingBasket shoppingBasket = shoppingBasketService.addArticle(userId, id);
            return new ResponseEntity<>(shoppingBasket, HttpStatus.CREATED);
        }
        catch (FindProductException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        catch (UserException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove article to user Shopping Basket
     *
     * @param userId User Id
     * @param id Product Id
     * @return update user Shopping Basket if success or Bad Request if fail
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable long userId, @PathVariable long id)
    {
        try {
            ShoppingBasket shoppingBasket = shoppingBasketService.removeArticle(userId, id);
            return new ResponseEntity<>(shoppingBasket, HttpStatus.OK);
        }
        catch (FindProductException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        catch (UserException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

}

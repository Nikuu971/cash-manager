package com.backend.controller;

import com.backend.model.Product;
import com.backend.service.abstractions.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Class Rest API Product Controller
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {
    public  static final String BASE_URL = "/api/v1/products";

    private final IProductService productService;

    /**
     * Product Controller constructor
     * Init product controller service
     * @param productService   Product Service
     */
    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * Create product
     *
     * @param product Request body how contain product informations
     * @return create product
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Product createProduct(@RequestBody Product product) {
        return this.productService.createProduct(product);
    }

    /**
     * Delete product
     *
     * @param id Product ID
     */
    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable long id) {
        this.productService.deleteProduct(id);
    }
}

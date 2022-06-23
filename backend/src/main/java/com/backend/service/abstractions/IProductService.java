package com.backend.service.abstractions;

import com.backend.model.Product;

public interface IProductService {
    Product getProductById(Long id);

    Product createProduct(Product product);

    void deleteProduct(Long id);
}

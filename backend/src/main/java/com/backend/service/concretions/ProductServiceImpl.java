package com.backend.service.concretions;

import com.backend.exception.FindProductException;
import com.backend.model.Product;
import com.backend.repository.ProductRepository;
import com.backend.service.abstractions.IProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class Product Service
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    /**
     * Product Service constructor
     * Init product service repository
     *
     * @param productRepository Product Service Repository
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get product by product Id
     *
     * @param id Product Id
     * @return product
     * @throws FindProductException if product Id is invalid
     */
    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (!product.isPresent()) {
            throw new FindProductException("Invalid Product Id");
        }
        return product.get();
    }

    /**
     * Create new product
     *
     * @param product Contain all product informations
     * @return created product
     */
    @Override
    public Product createProduct(Product product) {
        this.productRepository.save(product);
        return product;
    }

    /**
     * Delete product by product Id
     *
     * @param id Product Id
     */
    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
}

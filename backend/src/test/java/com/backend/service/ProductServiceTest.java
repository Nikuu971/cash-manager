package com.backend.service;

import com.backend.exception.FindProductException;
import com.backend.model.Product;
import com.backend.repository.ProductRepository;
import com.backend.service.concretions.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Class Product Service Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    /**
     * Test get product id method
     * fail if product isn't correct
     */
    @Test
    public void getProductById() {
        final Long id = 1L;
        Product p1 = new Product(4.8, "product");

        p1.setId(id);

        given(productRepository.findById(id)).willReturn(Optional.of(p1));

        Product expected = productService.getProductById(id);

        assertEquals(expected, p1);
    }

    /**
     * Test get product by id method error
     * fail if method doesn't throw
     */
    @Test(expected = FindProductException.class)
    public void getProductByIdError() {
        final Long id = 1L;
        Product p1 = null;

        given(productRepository.findById(id)).willReturn(Optional.ofNullable(p1));

        Product expected = productService.getProductById(id);

        assertThat(expected).isNotNull();
    }

    /**
     * Test create product method
     * fail if product isn't valid
     */
    @Test
    public void createProduct() {
        Product p1 = new Product(4.8, "product");

        Product create = productService.createProduct(p1);

        assertEquals(create.getName(), p1.getName());
        assertEquals(create.getPrice(), p1.getPrice(), 0);
    }

    /**
     * Test delete product method
     * fail if delete isn't validated
     */
    @Test
    public void deleteProduct() {
        final Long id = 1L;

        productService.deleteProduct(id);
        productService.deleteProduct(id);

        verify(productRepository, times(2)).deleteById(id);
    }
}

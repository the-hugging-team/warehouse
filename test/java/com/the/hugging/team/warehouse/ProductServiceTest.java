package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.repositories.ProductRepository;
import com.the.hugging.team.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductServiceTest {

    private static Product product;
    private final ProductService productService = ProductService.getInstance();

    @Test
    @DisplayName("Should update product")
    void shouldUpdateProduct() {
        List<Product> products = ProductRepository.getInstance().getAll();
        Product existingProduct = products.get(1);
        existingProduct.setName("testUPDATE");
        productService.updateProduct(existingProduct);

        Assertions.assertTrue(productService.getAllProducts().contains(existingProduct));
    }

    @Test
    @DisplayName("Should delete product")
    void shouldDeleteProduct() {
        productService.deleteProduct(product);

        Assertions.assertFalse(productService.getAllProducts().contains(product));
    }
}

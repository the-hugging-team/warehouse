package com.the.hugging.team.services;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.repositories.ProductRepository;

import java.util.List;

public class ProductService {

    private static final ProductRepository productRepository = ProductRepository.getInstance();
    private static ProductService INSTANCE = null;

    public static ProductService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductService();
        }

        return INSTANCE;
    }

    public List<Product> getProductsByShelf(Shelf shelf) {
        return productRepository.getByShelf(shelf);
    }
    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public Product addProduct(Product product)
    {
        productRepository.save(product);
        return product;
    }

    public void updateProduct(Product product)
    {
        productRepository.update(product);
    }

    public void deleteProduct(Product product) { productRepository.delete(product); }
}

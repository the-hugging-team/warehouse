package com.the.hugging.team.services;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.repositories.ProductRepository;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductService {

    private static final ProductRepository productRepository = ProductRepository.getInstance();
    private static final NotificationService notificationService = NotificationService.getInstance();
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

    public List<Product> getProductsByProductCategoryType(String productCategoryTypeSlug) {
        return productRepository.getByProductCategoryType(productCategoryTypeSlug);
    }

    public void updateProductsFromSellBean(ObservableList<Product> products) {
        for (Product beanProduct : products) {
            productRepository.update(beanProduct);

            if (beanProduct.getQuantity() <= 3 && beanProduct.getQuantity() != 0) {
                notificationService.sendNotification(notificationService.getNotificationTypeBySlug("notification_types.product_reached_minimum_amount"), beanProduct.getName());
            } else if (beanProduct.getQuantity() == 0) {
                notificationService.sendNotification(notificationService.getNotificationTypeBySlug("notification_types.product_out_of_stock"), beanProduct.getName());
            }
        }
    }

    public void updateProductsFromDeliveryBean(ObservableList<Product> products) {
        for (Product beanProduct : products) {
            Product dbProduct = productRepository.getById(beanProduct.getId()).orElse(null);
            if (dbProduct != null) {
                dbProduct.setQuantity(dbProduct.getQuantity() + beanProduct.getQuantity());
                productRepository.update(dbProduct);
            }
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}

package com.the.hugging.team.services;

import com.the.hugging.team.entities.ProductCategory;
import com.the.hugging.team.repositories.ProductCategoryRepository;

import java.util.List;

public class ProductCategoryService {
    private static ProductCategoryService INSTANCE;
    private ProductCategoryRepository productCategoryRepository = ProductCategoryRepository.getInstance();

    public static ProductCategoryService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ProductCategoryService();
        }
        return INSTANCE;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.getAll();
    }
}

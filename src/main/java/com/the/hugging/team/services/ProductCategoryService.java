package com.the.hugging.team.services;

import com.the.hugging.team.entities.ProductCategory;
import com.the.hugging.team.repositories.ProductCategoryRepository;

import java.util.List;

public class ProductCategoryService {

    private static final ProductCategoryRepository productCategoryRepository = ProductCategoryRepository.getInstance();
    private static ProductCategoryService INSTANCE = null;

    public static ProductCategoryService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductCategoryService();
        }

        return INSTANCE;
    }

    public List<ProductCategory> getAllProductCategories()
    {
        return productCategoryRepository.getAll();
    }
}

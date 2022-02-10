package com.the.hugging.team.services;

import com.the.hugging.team.entities.ProductQuantityType;
import com.the.hugging.team.repositories.ProductQuantityTypeRepository;

import java.util.List;

public class ProductQuantityTypeService {

    private static final ProductQuantityTypeRepository productQuantityTypeRepository = ProductQuantityTypeRepository.getInstance();
    private static ProductQuantityTypeService INSTANCE = null;

    public static ProductQuantityTypeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductQuantityTypeService();
        }

        return INSTANCE;
    }

    public ProductQuantityType addProductQuantityType(ProductQuantityType productQuantityType) {
        productQuantityTypeRepository.save(productQuantityType);
        return productQuantityType;
    }

    public List<ProductQuantityType> getAllProductQuantityTypes() {
        return productQuantityTypeRepository.getAll();
    }
}

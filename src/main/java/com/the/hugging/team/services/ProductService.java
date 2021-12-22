package com.the.hugging.team.services;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.repositories.ProductRepository;
import com.the.hugging.team.utils.wizard.beans.SellBean;

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

    public List<Product> getProductsByProductCategoryType(String productCategoryTypeSlug) {
        return productRepository.getByProductCategoryType(productCategoryTypeSlug);
    }

    public void updateProductsFromSellBean(SellBean sellBean) {
        for (Product beanProduct : sellBean.getSearchData()) {
            productRepository.update(beanProduct);
        }
    }

}

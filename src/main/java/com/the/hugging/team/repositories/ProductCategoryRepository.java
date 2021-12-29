package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.ProductCategory;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductCategoryRepository implements ObjectRepository<ProductCategory> {

    private final static Logger log = LogManager.getLogger(ProductCategoryRepository.class);
    private static ProductCategoryRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ProductCategoryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductCategoryRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(ProductCategory obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product category save error: " + e.getMessage());
        }
    }

    @Override
    public void update(ProductCategory obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product category update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(ProductCategory obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product category delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<ProductCategory> getById(int id) {
        ProductCategory productCategory = null;
        try {
            entityManager.getTransaction().begin();
            productCategory = entityManager.find(ProductCategory.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get productCategory by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(productCategory);
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> AllProductCategory = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            AllProductCategory.addAll(entityManager.createQuery("SELECT t FROM ProductCategory t", ProductCategory.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all product categories error: " + e.getMessage());
        }
        return AllProductCategory;
    }
}


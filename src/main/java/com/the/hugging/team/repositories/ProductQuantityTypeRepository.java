package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.ProductQuantityType;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductQuantityTypeRepository implements ObjectRepository<ProductQuantityType> {

    private final static Logger log = LogManager.getLogger(ProductQuantityTypeRepository.class);
    private static ProductQuantityTypeRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ProductQuantityTypeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductQuantityTypeRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(ProductQuantityType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product quantity type save error: " + e.getMessage());
        }
    }

    @Override
    public void update(ProductQuantityType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product quantity type update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(ProductQuantityType obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product quantity type delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<ProductQuantityType> getById(int id) {
        ProductQuantityType productQuantityType = null;
        try {
            entityManager.getTransaction().begin();
            productQuantityType = entityManager.find(ProductQuantityType.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get productQuantityType by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(productQuantityType);
    }

    @Override
    public List<ProductQuantityType> getAll() {
        List<ProductQuantityType> allProductQuantityTypes = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allProductQuantityTypes.addAll(entityManager.createQuery("SELECT t FROM ProductQuantityType t", ProductQuantityType.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all product quantity types error: " + e.getMessage());
        }
        return allProductQuantityTypes;
    }
}


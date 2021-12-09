package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements ObjectRepository<Product> {

    private final static Logger log = LogManager.getLogger(ProductRepository.class);
    private static ProductRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ProductRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Product obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Product obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Product obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Product delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> getById(int id) {
        Product product = null;
        try {
            entityManager.getTransaction().begin();
            product = entityManager.find(Product.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get product by Id error: " + e.getMessage());
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> AllProducts = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            AllProducts.addAll(entityManager.createQuery("SELECT t FROM Product t", Product.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all products error: " + e.getMessage());
        }
        return AllProducts;
    }

    public List<Product> getByShelf(Shelf shelf) {
        List<Product> products = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            products.addAll(entityManager.createQuery("SELECT t FROM Product t WHERE t.shelf = :shelf", Product.class).setParameter("shelf", shelf).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get products by shelf error: " + e.getMessage());
        }
        return products;
    }
}


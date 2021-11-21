package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class ProductRepository implements ObjectRepository<Product> {

    private final static Logger log = LogManager.getLogger(ProductCategoryRepository.class);
    private static final ProductCategoryRepository INSTANCE = new ProductCategoryRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ProductCategoryRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Product obj)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Product save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Product obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Product update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Product obj) {
        try
        {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Product delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Product> getById(int id) {
        Product product = null;
        try
        {
            entityManager.getTransaction().begin();
            product = entityManager.find(Product.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get product by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> AllProducts = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            AllProducts.addAll(entityManager.createQuery("SELECT t FROM Product t", Product.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all products error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return AllProducts;
    }
}


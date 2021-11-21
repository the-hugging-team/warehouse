package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Sale;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class SaleRepository implements ObjectRepository<Sale> {

    private final static Logger log = LogManager.getLogger(SaleRepository.class);
    private static final SaleRepository INSTANCE = new SaleRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static SaleRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Sale obj)
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
            log.error("Sale save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Sale obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Room update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Sale obj) {
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
            log.error("Sale delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Sale> getById(int id) {
        Sale sale = null;
        try
        {
            entityManager.getTransaction().begin();
            sale = entityManager.find(Sale.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get sale by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(sale);
    }

    @Override
    public List<Sale> getAll() {
        List<Sale> allSales = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allSales.addAll(entityManager.createQuery("SELECT t FROM Sale t", Sale.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all sales error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allSales;
    }
}


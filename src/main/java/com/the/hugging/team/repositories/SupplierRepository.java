package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Supplier;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class SupplierRepository implements ObjectRepository<Supplier> {

    private final static Logger log = LogManager.getLogger(SupplierRepository.class);
    private static final SupplierRepository INSTANCE = new SupplierRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static SupplierRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Supplier obj)
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
            log.error("Supplier save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Supplier obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Supplier update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Supplier obj) {
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
            log.error("Supplier delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Supplier> getById(int id) {
        Supplier supplier = null;
        try
        {
            entityManager.getTransaction().begin();
            supplier = entityManager.find(Supplier.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get supplier by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(supplier);
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> allSuppliers = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allSuppliers.addAll(entityManager.createQuery("SELECT t FROM Supplier t", Supplier.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all suppliers error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allSuppliers;
    }
}


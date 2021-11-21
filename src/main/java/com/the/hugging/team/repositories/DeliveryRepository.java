package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Delivery;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class DeliveryRepository implements ObjectRepository<Delivery> {

    private final static Logger log = LogManager.getLogger(InvoiceRepository.class);
    private static final InvoiceRepository INSTANCE = new InvoiceRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static InvoiceRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Delivery obj)
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
            log.error("Delivery save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Delivery obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Delivery update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Delivery obj) {
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
            log.error("Delivery delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Delivery> getById(int id) {
        Delivery delivery = null;
        try
        {
            entityManager.getTransaction().begin();
            delivery = entityManager.find(Delivery.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get delivery by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(delivery);
    }

    @Override
    public List<Delivery> getAll() {
        List<Delivery> allDeliveries = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allDeliveries.addAll(entityManager.createQuery("SELECT t FROM Delivery t", Delivery.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all deliveries error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allDeliveries;
    }
}


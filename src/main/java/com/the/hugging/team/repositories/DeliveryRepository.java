package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Delivery;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DeliveryRepository implements ObjectRepository<Delivery> {

    private final static Logger log = LogManager.getLogger(DeliveryRepository.class);
    private static DeliveryRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static DeliveryRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeliveryRepository();
        }

        return INSTANCE;
    }

    @Override
    public void save(Delivery obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Delivery save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Delivery obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Delivery update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Delivery obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Delivery delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Delivery> getById(int id) {
        Delivery delivery = null;
        try {
            entityManager.getTransaction().begin();
            delivery = entityManager.find(Delivery.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get delivery by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(delivery);
    }

    @Override
    public List<Delivery> getAll() {
        List<Delivery> allDeliveries = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allDeliveries.addAll(entityManager.createQuery("SELECT t FROM Delivery t", Delivery.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all deliveries error: " + e.getMessage());
        }
        return allDeliveries;
    }
}


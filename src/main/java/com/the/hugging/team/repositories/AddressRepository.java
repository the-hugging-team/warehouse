package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AddressRepository implements ObjectRepository<Address> {

    private final static Logger log = LogManager.getLogger(AddressRepository.class);
    private static AddressRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static AddressRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddressRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Address obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Address save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Address obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Address update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Address obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Address delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Address> getById(int id) {
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            address = entityManager.find(Address.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get address by Id error: " + e.getMessage());
        }
        return Optional.of(address);
    }

    @Override
    public List<Address> getAll() {
        List<Address> allAddresses = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allAddresses.addAll(entityManager.createQuery("SELECT t FROM Address t", Address.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all addresses error: " + e.getMessage());
        }
        return allAddresses;
    }
}

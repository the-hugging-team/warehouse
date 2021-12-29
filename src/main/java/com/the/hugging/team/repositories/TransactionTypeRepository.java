package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.TransactionType;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TransactionTypeRepository implements ObjectRepository<TransactionType> {

    private final static Logger log = LogManager.getLogger(TransactionTypeRepository.class);
    private static TransactionTypeRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static TransactionTypeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransactionTypeRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(TransactionType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("TransactionType save error: " + e.getMessage());
        }
    }

    @Override
    public void update(TransactionType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("TransactionType update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(TransactionType obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("TransactionType delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<TransactionType> getById(int id) {
        TransactionType transactionType = null;
        try {
            entityManager.getTransaction().begin();
            transactionType = entityManager.find(TransactionType.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get transactionType by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(transactionType);
    }

    @Override
    public List<TransactionType> getAll() {
        List<TransactionType> allTransactionTypes = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allTransactionTypes.addAll(entityManager.createQuery("SELECT t FROM TransactionType t", TransactionType.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all transaction types error: " + e.getMessage());
        }
        return allTransactionTypes;
    }

    public Optional<TransactionType> getBySlug(String slug) {
        TransactionType transactionType = null;
        try {
            entityManager.getTransaction().begin();
            transactionType = entityManager.createQuery("SELECT t FROM TransactionType t where t.slug = :slug", TransactionType.class).setParameter("slug", slug).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get transactionType by slug error: " + e.getMessage());
        }
        return Optional.ofNullable(transactionType);
    }
}


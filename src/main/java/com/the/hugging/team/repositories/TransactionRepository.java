package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Transaction;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class TransactionRepository implements ObjectRepository<Transaction> {

    private final static Logger log = LogManager.getLogger(TransactionRepository.class);
    private static final TransactionRepository INSTANCE = new TransactionRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static TransactionRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Transaction obj)
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
            log.error("Transaction save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Transaction obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Transaction update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Transaction obj) {
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
            log.error("Transaction delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Transaction> getById(int id) {
        Transaction transaction = null;
        try
        {
            entityManager.getTransaction().begin();
            transaction = entityManager.find(Transaction.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get transaction by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> allTransactions = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allTransactions.addAll(entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all transactions error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allTransactions;
    }
}


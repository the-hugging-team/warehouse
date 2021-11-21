package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class CashRegisterRepository implements ObjectRepository<CashRegister> {

    private final static Logger log = LogManager.getLogger(ClientRepository.class);
    private static final ClientRepository INSTANCE = new ClientRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ClientRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(CashRegister obj)
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
            log.error("Cash register save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(CashRegister obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Cash register update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(CashRegister obj) {
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
            log.error("Cash register delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<CashRegister> getById(int id) {
        CashRegister cashRegister = null;
        try
        {
            entityManager.getTransaction().begin();
            cashRegister = entityManager.find(CashRegister.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get cash register by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(cashRegister);
    }

    @Override
    public List<CashRegister> getAll() {
        List<CashRegister> allCashRegisters = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allCashRegisters.addAll(entityManager.createQuery("SELECT t FROM CashRegister t", CashRegister.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all cash register error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allCashRegisters;
    }
}


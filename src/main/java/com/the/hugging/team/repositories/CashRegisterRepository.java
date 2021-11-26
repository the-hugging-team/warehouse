package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CashRegisterRepository implements ObjectRepository<CashRegister> {

    private final static Logger log = LogManager.getLogger(CashRegisterRepository.class);
    private static CashRegisterRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static CashRegisterRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CashRegisterRepository();
        }

        return INSTANCE;
    }

    @Override
    public void save(CashRegister obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Cash register save error: " + e.getMessage());
        }
    }

    @Override
    public void update(CashRegister obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Cash register update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(CashRegister obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Cash register delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<CashRegister> getById(int id) {
        CashRegister cashRegister = null;
        try {
            entityManager.getTransaction().begin();
            cashRegister = entityManager.find(CashRegister.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get cash register by Id error: " + e.getMessage());
        }
        return Optional.of(cashRegister);
    }

    @Override
    public List<CashRegister> getAll() {
        List<CashRegister> allCashRegisters = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allCashRegisters.addAll(entityManager.createQuery("SELECT t FROM CashRegister t", CashRegister.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all cash register error: " + e.getMessage());
        }
        return allCashRegisters;
    }
}


package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.Sale;
import com.the.hugging.team.entities.SaleProduct;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SaleRepository implements ObjectRepository<Sale> {

    private final static Logger log = LogManager.getLogger(SaleRepository.class);
    private static SaleRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static SaleRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SaleRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Sale obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Sale save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Sale obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Room update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Sale obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Sale delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sale> getById(int id) {
        Sale sale = null;
        try {
            entityManager.getTransaction().begin();
            sale = entityManager.find(Sale.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get sale by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(sale);
    }

    @Override
    public List<Sale> getAll() {
        List<Sale> allSales = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allSales.addAll(entityManager.createQuery("SELECT t FROM Sale t", Sale.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all sales error: " + e.getMessage());
        }
        return allSales;
    }

    public void saveSaleProduct(SaleProduct saleProduct) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(saleProduct);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("SaleProduct save error: " + e.getMessage());
        }
    }

    public List<Sale> getByCashRegister(CashRegister cr) {
        List<Sale> cashRegisterSpecificSales = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            cashRegisterSpecificSales.addAll(entityManager.createQuery("SELECT t FROM Sale t WHERE t.cashRegister = :cashRegister", Sale.class).setParameter("cashRegister", cr).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get cash register specific transactions error: " + e.getMessage());
        }
        return cashRegisterSpecificSales;
    }
}


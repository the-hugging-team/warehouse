package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InvoiceRepository implements ObjectRepository<Invoice> {

    private final static Logger log = LogManager.getLogger(InvoiceRepository.class);
    private static InvoiceRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static InvoiceRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceRepository();
        }

        return INSTANCE;
    }

    @Override
    public void save(Invoice obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Invoice save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Invoice obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Invoice update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Invoice obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Invoice delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Invoice> getById(int id) {
        Invoice invoice = null;
        try {
            entityManager.getTransaction().begin();
            invoice = entityManager.find(Invoice.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get invoice by Id error: " + e.getMessage());
        }
        return Optional.of(invoice);
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> allInvoices = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allInvoices.addAll(entityManager.createQuery("SELECT t FROM Invoice t", Invoice.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all invoices error: " + e.getMessage());
        }
        return allInvoices;
    }
}


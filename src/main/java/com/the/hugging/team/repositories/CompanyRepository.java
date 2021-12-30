package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Company;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements ObjectRepository<Company> {
    private final static Logger log = LogManager.getLogger(CompanyRepository.class);
    private static CompanyRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static CompanyRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompanyRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Company obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Company save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Company obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Company update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Company obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Company delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Company> getById(int id) {
        Company company = null;
        try {
            entityManager.getTransaction().begin();
            company = entityManager.find(Company.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get company by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(company);
    }

    @Override
    public List<Company> getAll() {
        List<Company> allCompanies = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allCompanies.addAll(entityManager.createQuery("SELECT t FROM Company t", Company.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all companies error: " + e.getMessage());
        }
        return allCompanies;
    }
}

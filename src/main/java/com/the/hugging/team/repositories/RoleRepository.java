package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepository implements ObjectRepository<Role> {

    private final static Logger log = LogManager.getLogger(RoleRepository.class);
    private static RoleRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static RoleRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Role obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Role save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Role obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Role update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Role obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Role delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Role> getById(int id) {
        Role role = null;
        try {
            entityManager.getTransaction().begin();
            role = entityManager.find(Role.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get role by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(role);
    }

    public Optional<Role> getBySlug(String slug) {
        Role role = null;
        try {
            entityManager.getTransaction().begin();
            role = entityManager.createQuery("SELECT t FROM Role t where t.slug = :slug", Role.class).setParameter("slug", slug).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get role by slug error: " + e.getMessage());
        }
        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> getAll() {
        List<Role> AllProducts = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            AllProducts.addAll(entityManager.createQuery("SELECT t FROM Role t", Role.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all roles error: " + e.getMessage());
        }
        return AllProducts;
    }
}


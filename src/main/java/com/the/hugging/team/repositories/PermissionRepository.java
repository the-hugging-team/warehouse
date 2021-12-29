package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Permission;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PermissionRepository implements ObjectRepository<Permission> {

    private final static Logger log = LogManager.getLogger(PermissionRepository.class);
    private static PermissionRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static PermissionRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PermissionRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Permission obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Permission save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Permission obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Permission update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Permission obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Permission delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Permission> getById(int id) {
        Permission permission = null;
        try {
            entityManager.getTransaction().begin();
            permission = entityManager.find(Permission.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get permission by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(permission);
    }

    @Override
    public List<Permission> getAll() {
        List<Permission> allPermissions = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allPermissions.addAll(entityManager.createQuery("SELECT t FROM Permission t", Permission.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all permissions error: " + e.getMessage());
        }
        return allPermissions;
    }
}


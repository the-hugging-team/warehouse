package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Permission;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class PermissionRepository implements ObjectRepository<Permission> {

    private final static Logger log = LogManager.getLogger(PermissionRepository.class);
    private static final PermissionRepository INSTANCE = new PermissionRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static PermissionRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Permission obj)
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
            log.error("Permission save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Permission obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Permission update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Permission obj) {
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
            log.error("Permission delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Permission> getById(int id) {
        Permission permission = null;
        try
        {
            entityManager.getTransaction().begin();
            permission = entityManager.find(Permission.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get permission by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(permission);
    }

    @Override
    public List<Permission> getAll() {
        List<Permission> allPermissions = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allPermissions.addAll(entityManager.createQuery("SELECT t FROM Permission t", Permission.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all permissions error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allPermissions;
    }
}


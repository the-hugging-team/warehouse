package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class RoleRepository implements ObjectRepository<Role> {

    private final static Logger log = LogManager.getLogger(RoleRepository.class);
    private static final RoleRepository INSTANCE = new RoleRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static RoleRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Role obj)
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
            log.error("Role save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Role obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Role update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Role obj) {
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
            log.error("Role delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Role> getById(int id) {
        Role role = null;
        try
        {
            entityManager.getTransaction().begin();
            role = entityManager.find(Role.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get role by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(role);
    }

    @Override
    public List<Role> getAll() {
        List<Role> AllProducts = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            AllProducts.addAll(entityManager.createQuery("SELECT t FROM Role t", Role.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all roles error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return AllProducts;
    }
}


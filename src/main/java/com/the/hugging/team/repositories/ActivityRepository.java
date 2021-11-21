package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Activity;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class ActivityRepository implements ObjectRepository<Activity> {

    private final static Logger log = LogManager.getLogger(ClientRepository.class);
    private static final ClientRepository INSTANCE = new ClientRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ClientRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Activity obj)
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
            log.error("Activity save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Activity obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Activity update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Activity obj) {
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
            log.error("Activity delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Activity> getById(int id) {
        Activity activity = null;
        try
        {
            entityManager.getTransaction().begin();
            activity = entityManager.find(Activity.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get activity by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(activity);
    }

    @Override
    public List<Activity> getAll() {
        List<Activity> allActivities = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allActivities.addAll(entityManager.createQuery("SELECT t FROM Activity t", Activity.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all activities error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allActivities;
    }
}


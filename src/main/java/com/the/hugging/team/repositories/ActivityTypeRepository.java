package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.ActivityType;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class ActivityTypeRepository implements ObjectRepository<ActivityType> {

    private final static Logger log = LogManager.getLogger(ActivityTypeRepository.class);
    private static final ActivityTypeRepository INSTANCE = new ActivityTypeRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ActivityTypeRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(ActivityType obj)
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
            log.error("Activity type save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(ActivityType obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Activity type update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(ActivityType obj) {
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
            log.error("Activity type delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<ActivityType> getById(int id) {
        ActivityType activityType = null;
        try
        {
            entityManager.getTransaction().begin();
            activityType = entityManager.find(ActivityType.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get activity type by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(activityType);
    }

    @Override
    public List<ActivityType> getAll() {
        List<ActivityType> allActivityTypes = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allActivityTypes.addAll(entityManager.createQuery("SELECT t FROM ActivityType t", ActivityType.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all activity types error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allActivityTypes;
    }
}


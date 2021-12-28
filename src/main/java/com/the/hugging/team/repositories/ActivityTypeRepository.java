package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.ActivityType;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ActivityTypeRepository implements ObjectRepository<ActivityType> {

    private final static Logger log = LogManager.getLogger(ActivityTypeRepository.class);
    private static ActivityTypeRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ActivityTypeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityTypeRepository();
        }

        return INSTANCE;
    }

    @Override
    public void save(ActivityType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity type save error: " + e.getMessage());
        }
    }

    @Override
    public void update(ActivityType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity type update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(ActivityType obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity type delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<ActivityType> getById(int id) {
        ActivityType activityType = null;
        try {
            entityManager.getTransaction().begin();
            activityType = entityManager.find(ActivityType.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get activity type by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(activityType);
    }

    @Override
    public List<ActivityType> getAll() {
        List<ActivityType> allActivityTypes = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allActivityTypes.addAll(entityManager.createQuery("SELECT t FROM ActivityType t", ActivityType.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all activity types error: " + e.getMessage());
        }
        return allActivityTypes;
    }

    public Optional<ActivityType> getBySlug(String slug)
    {
        ActivityType activityType = null;
        try {
            entityManager.getTransaction().begin();
            activityType = entityManager.createQuery("SELECT t FROM ActivityType t where t.slug = :slug", ActivityType.class).setParameter("slug", slug).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get Activity Type by slug error: " + e.getMessage());
        }
        return Optional.of(activityType);
    }
}


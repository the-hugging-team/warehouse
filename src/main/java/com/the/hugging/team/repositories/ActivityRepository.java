package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Activity;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ActivityRepository implements ObjectRepository<Activity> {

    private final static Logger log = LogManager.getLogger(ActivityRepository.class);
    private static ActivityRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ActivityRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Activity obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Activity obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Activity obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Activity delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Activity> getById(int id) {
        Activity activity = null;
        try {
            entityManager.getTransaction().begin();
            activity = entityManager.find(Activity.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get activity by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(activity);
    }

    @Override
    public List<Activity> getAll() {
        List<Activity> allActivities = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allActivities.addAll(entityManager.createQuery("SELECT t FROM Activity t", Activity.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all activities error: " + e.getMessage());
        }
        return allActivities;
    }

    public List<Activity> getByUser(User user) {
        List<Activity> userSpecificActivities = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            userSpecificActivities.addAll(entityManager.createQuery("SELECT t FROM Activity t WHERE t.user = :user", Activity.class).setParameter("user", user).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get user specific activities error: " + e.getMessage());
        }
        return userSpecificActivities;
    }
}


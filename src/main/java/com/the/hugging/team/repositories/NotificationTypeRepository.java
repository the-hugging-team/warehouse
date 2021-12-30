package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.NotificationType;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationTypeRepository implements ObjectRepository<NotificationType> {

    private final static Logger log = LogManager.getLogger(NotificationTypeRepository.class);
    private static NotificationTypeRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static NotificationTypeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationTypeRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(NotificationType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("NotificationType save error: " + e.getMessage());
        }
    }

    @Override
    public void update(NotificationType obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("NotificationType update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(NotificationType obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("NotificationType delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<NotificationType> getById(int id) {
        NotificationType notification = null;
        try {
            entityManager.getTransaction().begin();
            notification = entityManager.find(NotificationType.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get notification by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(notification);
    }

    @Override
    public List<NotificationType> getAll() {
        List<NotificationType> allNotificationTypes = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allNotificationTypes.addAll(entityManager.createQuery("SELECT t FROM NotificationType t", NotificationType.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get notifications error: " + e.getMessage());
        }
        return allNotificationTypes;
    }

    public Optional<NotificationType> getNotificationTypeBySlug(String slug) {
        NotificationType notificationType = null;
        try {
            entityManager.getTransaction().begin();
            notificationType = entityManager.createQuery("SELECT n FROM NotificationType n WHERE n.slug = :slug", NotificationType.class).setParameter("slug", slug).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get notification by slug error: " + e.getMessage());
        }
        return Optional.ofNullable(notificationType);
    }
}


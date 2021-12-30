package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Notification;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class NotificationRepository implements ObjectRepository<Notification> {

    private final static Logger log = LogManager.getLogger(NotificationRepository.class);
    private static NotificationRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static NotificationRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Notification obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Notification save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Notification obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Notification update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Notification obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Notification delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Notification> getById(int id) {
        Notification notification = null;
        try {
            entityManager.getTransaction().begin();
            notification = entityManager.find(Notification.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get notification by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(notification);
    }

    @Override
    public List<Notification> getAll() {
        List<Notification> allNotifications = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allNotifications.addAll(entityManager.createQuery("SELECT t FROM Notification t", Notification.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get notifications error: " + e.getMessage());
        }
        return allNotifications;
    }

    public List<Notification> getAllByUser(User user) {
        List<Notification> allNotifications = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allNotifications.addAll(entityManager.createQuery("SELECT t FROM Notification t WHERE t.user = :user", Notification.class).setParameter("user", user).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all notifications by user error: " + e.getMessage());
        }
        return allNotifications;
    }

    public List<Notification> getUnreadByUser(User user) {
        List<Notification> allUnreadNotifications = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allUnreadNotifications.addAll(entityManager.createQuery("SELECT t FROM Notification t WHERE t.user = :user AND t.readAt IS NULL", Notification.class).setParameter("user", user).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get unread notifications by user error: " + e.getMessage());
        }
        return allUnreadNotifications;
    }

}


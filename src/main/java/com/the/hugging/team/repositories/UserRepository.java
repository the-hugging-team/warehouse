package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

public class UserRepository implements ObjectRepository<User> {

    private final static Logger log = LogManager.getLogger(UserRepository.class);
    private static UserRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(User obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("User save error: " + e.getMessage());
        }
    }

    @Override
    public void update(User obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("User update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(User obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("User delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> getById(int id) {
        User user = null;
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get user by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new LinkedList<>();
        try {
            entityManager.getTransaction().begin();
            allUsers.addAll(entityManager.createQuery("SELECT t FROM User t", User.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get all users error: " + e.getMessage());
        }
        return allUsers;
    }

    public User getByUsername(String username) {
        User authUser = null;
        String query = "SELECT t FROM User t WHERE t.username = :Username";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("Username", username);
        try {
            entityManager.getTransaction().begin();
            authUser = typedQuery.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get User by username error: " + e.getMessage());
        }
        return authUser;
    }

    public List<User> getByRoles(Set<Role> roles) {
        List<User> users = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            users.addAll(entityManager.createQuery("SELECT t FROM User t WHERE t.role IN (:roles)", User.class).setParameter("roles", roles).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get users error: " + e.getMessage());
        }
        return users;
    }
}

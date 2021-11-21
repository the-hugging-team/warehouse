package com.the.hugging.team.repositories;

import com.the.hugging.team.utils.Connection;
import com.the.hugging.team.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserRepository implements ObjectRepository<User> {

    private final static Logger log = LogManager.getLogger(UserRepository.class);
    private static final UserRepository INSTANCE = new UserRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static UserRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(User obj)
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
            log.error("User save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(User obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("User update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(User obj) {
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
            log.error("User delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<User> getById(int id) {
        User user = null;
        try
        {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get user by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allUsers.addAll(entityManager.createQuery("SELECT t FROM User t", User.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all users error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allUsers;
    }

    public User getByUsername(String username)
    {
        entityManager = Connection.getEntityManager();
        User authUser = null;
        String query = "SELECT t FROM User t WHERE t.username = :Username";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("Username", username);
        try
        {
            entityManager.getTransaction().begin();
            authUser = typedQuery.getSingleResult();
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get User by username error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return authUser;
    }
}

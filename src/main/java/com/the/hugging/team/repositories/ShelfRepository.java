package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class ShelfRepository implements ObjectRepository<Shelf> {

    private final static Logger log = LogManager.getLogger(ShelfRepository.class);
    private static final ShelfRepository INSTANCE = new ShelfRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ShelfRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Shelf obj)
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
            log.error("Shelf save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Shelf obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Shelf update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Shelf obj) {
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
            log.error("Shelf delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Shelf> getById(int id) {
        Shelf shelf = null;
        try
        {
            entityManager.getTransaction().begin();
            shelf = entityManager.find(Shelf.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get shelf by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(shelf);
    }

    @Override
    public List<Shelf> getAll() {
        List<Shelf> allShelves = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allShelves.addAll(entityManager.createQuery("SELECT t FROM Shelf t", Shelf.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get shelves error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allShelves;
    }
}

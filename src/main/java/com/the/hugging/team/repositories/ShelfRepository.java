package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShelfRepository implements ObjectRepository<Shelf> {

    private final static Logger log = LogManager.getLogger(ShelfRepository.class);
    private static ShelfRepository INSTANCE = null;
    private final EntityManager entityManager = Connection.getEntityManager();

    public static ShelfRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShelfRepository();
        }
        return INSTANCE;
    }

    @Override
    public void save(Shelf obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Shelf save error: " + e.getMessage());
        }
    }

    @Override
    public void update(Shelf obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Shelf update error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Shelf obj) {
        try {
            entityManager.getTransaction().begin();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Shelf delete error: " + e.getMessage());
        }
    }

    @Override
    public Optional<Shelf> getById(int id) {
        Shelf shelf = null;
        try {
            entityManager.getTransaction().begin();
            shelf = entityManager.find(Shelf.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get shelf by Id error: " + e.getMessage());
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public List<Shelf> getAll() {
        List<Shelf> allShelves = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            allShelves.addAll(entityManager.createQuery("SELECT t FROM Shelf t", Shelf.class).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get shelves error: " + e.getMessage());
        }
        return allShelves;
    }

    public List<Shelf> getShelvesByRoom(Room room) {
        List<Shelf> shelves = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            shelves.addAll(entityManager.createQuery("SELECT t FROM Shelf t WHERE t.room = :room", Shelf.class).setParameter("room", room).getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Get shelves by room error: " + e.getMessage());
        }
        return shelves;
    }
}


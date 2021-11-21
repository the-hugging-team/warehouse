package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Room;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

public class RoomRepository implements ObjectRepository<Room> {

    private final static Logger log = LogManager.getLogger(SaleRepository.class);
    private static final SaleRepository INSTANCE = new SaleRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static SaleRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Room obj)
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
            log.error("Room save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Room obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Room update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Room obj) {
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
            log.error("Room delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Room> getById(int id) {
        Room room = null;
        try
        {
            entityManager.getTransaction().begin();
            room = entityManager.find(Room.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get room by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(room);
    }

    @Override
    public List<Room> getAll() {
        List<Room> allRooms = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allRooms.addAll(entityManager.createQuery("SELECT t FROM Room t", Room.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all rooms error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allRooms;
    }
}


package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Client;
import com.the.hugging.team.utils.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import javax.persistence.EntityManager;

public class ClientRepository implements ObjectRepository<Client> {

    private final static Logger log = LogManager.getLogger(ClientRepository.class);
    private static final ClientRepository INSTANCE = new ClientRepository();
    private EntityManager entityManager = Connection.getEntityManager();

    public static ClientRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void save(Client obj)
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
            log.error("Client save error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void update(Client obj) {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Client update error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public void delete(Client obj) {
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
            log.error("Client delete error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
    }

    @Override
    public Optional<Client> getById(int id) {
        Client client = null;
        try
        {
            entityManager.getTransaction().begin();
            client = entityManager.find(Client.class, id);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get client by Id error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return Optional.of(client);
    }

    @Override
    public List<Client> getAll() {
        List<Client> allClient = new LinkedList<>();
        try
        {
            entityManager.getTransaction().begin();
            allClient.addAll(entityManager.createQuery("SELECT t FROM Client t", Client.class).getResultList());
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            log.error("Get all clients error: " + e.getMessage());
        }
        finally
        {
            entityManager.close();
        }
        return allClient;
    }
}


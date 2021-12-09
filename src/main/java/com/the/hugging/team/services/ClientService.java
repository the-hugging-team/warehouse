package com.the.hugging.team.services;

import com.the.hugging.team.entities.Client;
import com.the.hugging.team.repositories.ClientRepository;

import java.util.List;

public class ClientService {

    private static ClientService INSTANCE = null;
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    public static ClientService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientService();
        }

        return INSTANCE;
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public void setClientName(Client client, String name) {
        client.setName(name);
        clientRepository.update(client);
    }

    public Client addClient(String name) {
        Client client = new Client();
        client.setName(name);
        clientRepository.save(client);
        return client;
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }
}

package com.tina.hashina.tinaausbuy.service;

import com.tina.hashina.tinaausbuy.model.Client;
import com.tina.hashina.tinaausbuy.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Client createClient(Client client) {
        Client savedClient = clientRepository.save(client);
        log.info("New Client: {}", savedClient);
        return savedClient;
    }

    public boolean deleteClient(Long userId) {
        Optional<Client> client = clientRepository.findById(userId);
        if (client.isPresent()) {
            log.info("Client is deleted: {}", client.get());
            clientRepository.deleteById(userId);
            return true;
        } else {
            log.info("Client does not exist: {}", userId);
            return false;
        }
    }

    public Client updateClient(Long userId, Client client) {
        if (userId == null || client == null) {
            return null;
        }else if (!userId.equals(client.getUserId())) {
            return null;
        }

        Client clientInDB = findClientById(userId);
        if (clientInDB == null) {
            return null;
        }

        Client savedClient = clientRepository.save(client);
        log.info("Updated client: {}", savedClient);
        return savedClient;
    }

    public List<Client>  findAllClient() {
        List<Client> clients = clientRepository.findAll();
        clients.forEach(client -> log.info("Client Found: {}", client));
        return clients;
    }

    public Client findClientById(Long userId) {
        Optional<Client> client = clientRepository.findById(userId);
        if (client.isPresent()) {
            log.info("Client Found: {}", client.get());
            return client.get();
        }

        return null;
    }
}

package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.model.Client;
import com.tina.hashina.tinaausbuy.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.findAllClient();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{userId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long userId) {
        Client client = clientService.findClientById(userId);
        if (client == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        if (savedClient == null) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(savedClient, HttpStatus.OK);
    }

    @PutMapping("/clients/{userId}")
    public ResponseEntity<Client> updateClient(@PathVariable Long userId,
                                               @Valid @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(userId, client);
        if (updatedClient == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping("/clients/{userId}")
    public ResponseEntity<HttpStatus>  deleteClient(@PathVariable Long userId) {
        boolean deleteResult = clientService.deleteClient(userId);
        if (deleteResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

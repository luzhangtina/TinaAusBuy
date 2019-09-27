package com.tina.hashina.tinaausbuy.controller;

import com.sun.deploy.util.SessionState;
import com.tina.hashina.tinaausbuy.module.Client;
import com.tina.hashina.tinaausbuy.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        Client client = clientService.findClientId(userId);
        if (client == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}

package com.tina.hashina.tinaausbuy.service;

import com.tina.hashina.tinaausbuy.module.Client;
import com.tina.hashina.tinaausbuy.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Client createClient(String userName, String weChatId,
                               String aliPayId, String phoneNumber) {
        Client client = Client.builder()
                .userName(userName)
                .weChatId(weChatId)
                .aliPayId(aliPayId)
                .phoneNumber(phoneNumber)
                .build();

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

    public Boolean updateClientName(Client client, String userName) {
        if (userName == null || userName.length() <= 0 ) {
            log.warn("User name is invalid");
            return false;
        }

        client.setUserName(userName);
        clientRepository.save(client);
        log.info("Updated client: {}", client);
        return true;
    }

    public Boolean updateClientPhoneNumber(Client client, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() <= 0 ) {
            log.warn("Phone number is invalid");
            return false;
        }

        client.setPhoneNumber(phoneNumber);
        clientRepository.save(client);
        log.info("Updated client: {}", client);
        return true;
    }

    public Boolean updateClientWechatId(Client client, String wechatId) {
        if (wechatId == null || wechatId.length() <= 0 ) {
            log.warn("Wechat ID is invalid");
            return false;
        }

        client.setWeChatId(wechatId);
        clientRepository.save(client);
        log.info("Updated client: {}", client);
        return true;
    }

    public Boolean updateClientAlipayId(Client client, String alipayId) {
        if (alipayId == null ) {
            log.warn("Alipay ID is invalid");
            return false;
        }

        client.setAliPayId(alipayId);
        clientRepository.save(client);
        log.info("Updated client: {}", client);
        return true;
    }

    public List<Client> findClientsByName(String userName) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userName", exact());
        List<Client> clients = clientRepository.findAll(
                Example.of(Client.builder().userName(userName).build(), matcher));
        clients.forEach(client -> log.info("Client Found: {}", client));
        return clients;
    }

    public List<Client> findClientsByPhoneNumber(String phoneNumber) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("phoneNumber", exact());
        List<Client> clients = clientRepository.findAll(
                Example.of(Client.builder().phoneNumber(phoneNumber).build(), matcher));
        clients.forEach(client -> log.info("Client: {}", client));

        return clients;
    }

    public Optional<Client> findOneClientByWechatId(String weChatId) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("weChatId", exact());
        Optional<Client> client = clientRepository.findOne(
                Example.of(Client.builder().weChatId(weChatId).build(), matcher));
        if (client.isPresent()) {
            log.info("Client Found: {}", client.get());
        }

        return client;
    }

    public Optional<Client> findOneClientByNameAndPhoneNumber(String userName, String phoneNumber) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("userName", exact())
                .withMatcher("phoneNumber", exact());
        Optional<Client> client = clientRepository.findOne(
                Example.of(Client.builder().userName(userName).phoneNumber(phoneNumber).build(),
                        matcher));
        if (client.isPresent()) {
            log.info("Client Found: {}", client.get());
        }

        return client;
    }

    public List<Client>  findAllClient() {
        List<Client> clients = clientRepository.findAll();
        clients.forEach(client -> log.info("Client Found: {}", client));
        return clients;
    }

    public Client findClientId(Long userId) {
        Optional<Client> client = clientRepository.findById(userId);
        if (client.isPresent()) {
            log.info("Client Found: {}", client.get());
            return client.get();
        }

        return null;
    }
}

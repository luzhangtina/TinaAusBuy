package com.tina.hashina.tinaausbuy.repository;

import com.tina.hashina.tinaausbuy.module.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByUserName(String userName);
    List<Client> findByPhoneNumber(String phoneNumber);
}

package com.tina.hashina.tinaausbuy;

import com.tina.hashina.tinaausbuy.repository.ClientRepository;
import com.tina.hashina.tinaausbuy.repository.OrderHeadRepository;
import com.tina.hashina.tinaausbuy.repository.OrderLineRepository;
import com.tina.hashina.tinaausbuy.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class TinaAusBuyApplication implements ApplicationRunner {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderHeadRepository orderHeadRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(TinaAusBuyApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments applicationArguments) throws Exception {
    }

}

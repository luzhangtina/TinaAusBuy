package com.tina.hashina.tinaausbuy;

import com.tina.hashina.tinaausbuy.service.ClientService;
import com.tina.hashina.tinaausbuy.service.PostInfoService;
import com.tina.hashina.tinaausbuy.service.ProductService;
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
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PostInfoService postInfoService;

    public static void main(String[] args) {
        SpringApplication.run(TinaAusBuyApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments applicationArguments) throws Exception {
        clientService.findOneClientByWechatId("yu084133");
        clientService.findAllClient();
    }

}

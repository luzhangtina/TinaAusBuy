package com.tina.hashina.tinaausbuy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class TinaAusBuyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TinaAusBuyApplication.class, args);
    }

}

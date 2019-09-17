package com.tina.hashina.tinaausbuy;

import com.tina.hashina.tinaausbuy.module.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getProductByProductId_shouldReturnNullForNotExistProductId() {
        ResponseEntity<Product> responseEntity = testRestTemplate.getForEntity("/products/{productId}", Product.class, "1");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
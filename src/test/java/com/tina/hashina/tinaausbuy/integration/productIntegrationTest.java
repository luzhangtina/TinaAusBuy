package com.tina.hashina.tinaausbuy.integration;

import com.tina.hashina.tinaausbuy.model.MeasureUnit;
import com.tina.hashina.tinaausbuy.model.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class productIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProductService productService;

    private List<Product> products = new ArrayList<>();

    @Before
    public void setUp() {
        Product product = new Product("Test01",
                "测试产品01", 5,25, 25,
                MeasureUnit.GRAM);
        productService.createProduct(product);
        products.add(product);

        product = new Product("Test02",
                "测试产品02", 15,75, 100,
                MeasureUnit.MILLILITER);
        productService.createProduct(product);
        products.add(product);
    }

    @Test
    public void getProductByProductId_shouldReturnNoContentForNotExistProductId() {
        Product product = products.get(products.size() - 1);
        ResponseEntity<Product> responseEntity = testRestTemplate
                .getForEntity("/products/{productId}", Product.class, (product.getProductId() + 1));
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void getProductByProductId_shouldReturnProductForExistProductId() {
        Product product = products.get(1);
        ResponseEntity<Product> responseEntity = testRestTemplate
                .getForEntity("/products/{productId}", Product.class, product.getProductId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
    }

    @After
    public void tearDown() {
        products.forEach(product -> productService.deleteProduct(product.getProductId()));

        products.clear();
    }
}
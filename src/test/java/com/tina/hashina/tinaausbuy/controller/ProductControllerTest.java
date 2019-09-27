package com.tina.hashina.tinaausbuy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.hashina.tinaausbuy.model.MeasureUnit;
import com.tina.hashina.tinaausbuy.model.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductById_shouldReturnNoContentWhenProductNotExist() throws Exception {
        when(productService.getProductById(isNotNull())).thenReturn(null);
        this.mockMvc.perform(get("/products/{productId}", 1)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getProductById_shouldReturnProductWhenProductExist() throws Exception {
        Product product = new Product("Test01","测试产品01", 5,25, 25,
                MeasureUnit.GRAM);

        when(productService.getProductById(isNotNull())).thenReturn(product);
        this.mockMvc.perform(get("/products/{productId}", 1)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proNameEng").value("Test01"))
                .andExpect(jsonPath("$.priceAud").value(5))
                .andExpect(jsonPath("$.priceRmb").value(25));
    }

    @Test
    public void getProducts_shouldReturnEmptyForZeroProduct() throws Exception {
        when(productService.getProducts()).thenReturn(null);
        MvcResult mvcResult = this.mockMvc.perform(get("/products").accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getProducts_shouldReturnProducts() throws Exception {
        when(productService.getProducts()).thenReturn(Arrays.asList(
                new Product("Test01","测试产品01", 5,25, 25,
                        MeasureUnit.GRAM),
                new Product("Test02","测试产品02", 15,75, 100,
                        MeasureUnit.MILLILITER)));

        this.mockMvc.perform(get("/products").accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].proNameEng").value("Test01"))
                .andExpect(jsonPath("$.[1].proNameEng").value("Test02"));
    }

    @Test
    public void addProduct_shouldReturnProduct() throws Exception {
        Product product = new Product("Test01","测试产品01", 5,25, 25,
                MeasureUnit.GRAM);

        when(productService.createProduct(isNotNull())).thenReturn(product);

        this.mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.proNameEng").value("Test01"))
                .andExpect(jsonPath("$.priceAud").value(5))
                .andExpect(jsonPath("$.priceRmb").value(25));
    }

    @Test
    public void addProduct_shouldReturnBadRequestWhenNameIsNull() throws Exception {
        Product product = new Product(null,"测试产品01", 5,25, 25,
                MeasureUnit.GRAM);

        when(productService.createProduct(isNotNull())).thenReturn(product);

        this.mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addProduct_shouldReturnBadRequestWhenProductIsNull() throws Exception {
        when(productService.createProduct(isNotNull())).thenReturn(null);

        this.mockMvc.perform(post("/products")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addProduct_shouldReturn422tWhenFailToCreateProduct() throws Exception {
        Product product = new Product("Test01","测试产品01", 5,25, 25,
                MeasureUnit.GRAM);

        when(productService.createProduct(isNotNull())).thenReturn(null);

        this.mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateProduct_shouldReturn200() throws Exception {
        Product product = new Product("Test01","测试产品01", 5,25, 25,
                MeasureUnit.GRAM);

        when(productService.updateProduct(isNotNull(), isNotNull())).thenReturn(product);

        this.mockMvc.perform(put("/products/{productId}", 2)
                .content(objectMapper.writeValueAsString(product))
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proNameEng").value("Test01"))
                .andExpect(jsonPath("$.priceAud").value(5))
                .andExpect(jsonPath("$.priceRmb").value(25));
    }

    @Test
    public void deleteProduct_shouldReturnAcceptWhenProductExist() throws Exception {
        when(productService.deleteProduct(isNotNull())).thenReturn(true);

        this.mockMvc.perform(delete("/products/{productId}", 2)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct_shouldReturnAcceptWhenProductNotExist() throws Exception {
        when(productService.deleteProduct(isNotNull())).thenReturn(false);

        this.mockMvc.perform(delete("/products/{productId}", 2)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
package com.tina.hashina.tinaausbuy.Controller;

import com.tina.hashina.tinaausbuy.module.MeasureUnit;
import com.tina.hashina.tinaausbuy.module.Product;
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
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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
}
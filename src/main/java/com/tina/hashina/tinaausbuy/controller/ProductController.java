package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    @GetMapping("/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return this.productService.findProductByProductId(productId);
    }

    @PostMapping("/products")
    public Product addProduct(@Valid @RequestBody Product product) {
        log.info("{}", product);
        return this.productService.createProduct(product);
    }
}

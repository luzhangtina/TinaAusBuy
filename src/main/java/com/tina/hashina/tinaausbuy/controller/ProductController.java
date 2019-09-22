package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        List<Product> products = this.productService.getProducts();
        return products;
    }

    @GetMapping("/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        Product product = this.productService.findProductByProductId(productId);
        return product;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        Product savedProduct = this.productService.createProduct(product);
        return savedProduct;
    }
}

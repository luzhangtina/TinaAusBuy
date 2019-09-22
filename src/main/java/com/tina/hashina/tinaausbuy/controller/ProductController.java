package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return this.productService.createProduct(product);
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable Long productId,
                                 @Valid @RequestBody Product product) {
        return this.productService.updateProduct(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long productId) {
        if (this.productService.deleteProduct(productId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
}

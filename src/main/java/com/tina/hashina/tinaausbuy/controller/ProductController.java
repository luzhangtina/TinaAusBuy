package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.model.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = this.productService.getProducts();
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product savedProduct = this.productService.getProductById(productId);
        if (savedProduct == null) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(savedProduct, HttpStatus.OK);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product savedProduct= this.productService.createProduct(product);
        if ( savedProduct == null) {
            return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity(savedProduct, HttpStatus.CREATED);
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                 @Valid @RequestBody Product product) {
        Product savedProduct =  this.productService.updateProduct(productId, product);
        if (savedProduct == null) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(savedProduct, HttpStatus.OK);
        }
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

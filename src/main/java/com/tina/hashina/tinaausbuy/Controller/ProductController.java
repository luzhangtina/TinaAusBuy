package com.tina.hashina.tinaausbuy.Controller;

import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}

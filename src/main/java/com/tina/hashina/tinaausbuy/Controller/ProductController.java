package com.tina.hashina.tinaausbuy.Controller;

import com.tina.hashina.tinaausbuy.Exception.ProductNotFoundException;
import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/")
    public List<Product> getProducts() {
        List<Product> products = this.productService.getProducts();
        if (products == null) {
            throw new ProductNotFoundException();
        }
        return products;
    }

    @GetMapping("/products/{productId}")
    public Product getProductById(@PathVariable String productId) {
        Product product = this.productService.findProductByProductId(Long.valueOf(productId));
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }
}

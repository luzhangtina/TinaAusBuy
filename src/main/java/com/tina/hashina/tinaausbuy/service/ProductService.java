package com.tina.hashina.tinaausbuy.service;


import com.tina.hashina.tinaausbuy.module.Product;
import com.tina.hashina.tinaausbuy.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("Saved Product: {}", savedProduct);
        return savedProduct;
    }

    public Boolean deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            log.info("Product is deleted: {}", product.get());
            productRepository.deleteById(productId);
            return true;
        } else {
            log.info("Product does not exist: {}", productId);
            return false;
        }
    }

    public Boolean updateProductPrice(Product product,
                                      double priceAud,
                                      double priceRmb ) {
        if (priceAud < 0 || priceRmb < 0) {
            log.warn("Price is invalid");
            return false;
        }

        product.setPriceAud(priceAud);
        product.setPriceRmb(priceRmb);
        productRepository.save(product);
        log.info("Updated product: {}", product);
        return true;
    }

    public Boolean updateProductNameEng(Product product,
                                        String proNameEng ) {
        if (proNameEng.length() <= 0) {
            log.warn("Product name is invalid");
            return false;
        }

        product.setProNameEng(proNameEng);
        productRepository.save(product);
        log.info("Updated product: {}", product);
        return true;
    }

    public Boolean updateProductNameChn(Product product,
                                        String proNameChn ) {
        if (proNameChn == null || proNameChn.length() <= 0) {
            log.warn("Product name is invalid");
            return false;
        }

        product.setProNameChn(proNameChn);
        productRepository.save(product);
        log.info("Updated product: {}", product);
        return true;
    }

    public Product findProductByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            log.info("Product Found: {}", product.get());
            return product.get();
        }

        return null;
    }

    public List<Product> findProductByProductNameEng(String proNameEng) {
        List<Product> products = productRepository.findByProNameEng(proNameEng);
        products.forEach(product -> log.info("Product Found: {}", product));
        return products;
    }

    public List<Product> findProductByProductNameChn(String proNameChn) {
        List<Product> products = productRepository.findByProNameChn(proNameChn);
        products.forEach(product -> log.info("Product Found: {}", product));
        return products;
    }

    public List<Product> findProductLikeProductNameEng(String proNameEng) {
        List<Product> products = productRepository.findByProNameEngContaining(proNameEng);
        products.forEach(product -> log.info("Product Found: {}", product));
        return products;
    }

    public List<Product> findProductLikeProductNameChn(String proNameChn) {
        List<Product> products = productRepository.findByProNameChnContaining(proNameChn);
        products.forEach(product -> log.info("Product Found: {}", product));
        return products;
    }

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> log.info("Product Found: {}", product));
        return products;
    }
}

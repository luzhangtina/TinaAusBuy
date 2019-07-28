package com.tina.hashina.tinaausbuy.repository;

import com.tina.hashina.tinaausbuy.module.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProNameEng(String proNameEng);
    List<Product> findByProNameChn(String proNameChn);
    List<Product> findByProNameEngContaining(String proNameEng);
    List<Product> findByProNameChnContaining(String proNameChn);
}

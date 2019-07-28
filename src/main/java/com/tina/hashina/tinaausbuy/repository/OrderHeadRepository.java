package com.tina.hashina.tinaausbuy.repository;

import com.tina.hashina.tinaausbuy.module.OrderHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHeadRepository extends JpaRepository<OrderHead, Long> {
    List<OrderHead> findByClient_UserIdOrderByBookDateDesc(Long userId);
    List<OrderHead> findTop10ByOrderByBookDateDesc();
    List<OrderHead> findTop3ByClient_UserIdOrderByBookDateDesc(Long userId);
}

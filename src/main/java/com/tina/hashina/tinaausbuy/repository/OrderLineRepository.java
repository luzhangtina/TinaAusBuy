package com.tina.hashina.tinaausbuy.repository;

import com.tina.hashina.tinaausbuy.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByPackageNumberOrderByOrderLineNumberAsc(String packageNumber);
    List<OrderLine> findByOrderHead_OrderNumberOrderByOrderLineNumberAsc(Long orderNumber);
    List<String> findDistinctPackageNumberByOrderHead_OrderNumber(Long orderNumber);
}

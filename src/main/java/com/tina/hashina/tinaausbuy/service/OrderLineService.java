package com.tina.hashina.tinaausbuy.service;


import com.tina.hashina.tinaausbuy.model.*;
import com.tina.hashina.tinaausbuy.repository.OrderLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class OrderLineService {
    @Autowired
    OrderLineRepository orderLineRepository;

    public OrderLine createOrderLine(OrderHead orderHead, Product product, int quantity) {
        OrderLine orderLine = OrderLine.builder()
                .orderHead(orderHead)
                .product(product)
                .quantity(quantity)
                .state(OrderLineState.INIT)
                .build();
        OrderLine savedOrderLine = orderLineRepository.save(orderLine);
        log.info("New Order Line: {}", savedOrderLine);
        return savedOrderLine;
    }

    public boolean deleteOrderLine(Long orderLineNumber) {
        Optional<OrderLine> orderLine = orderLineRepository.findById(orderLineNumber);
        if (orderLine.isPresent()) {
            log.info("Order Line is deleted: {}", orderLine.get());
            orderLineRepository.deleteById(orderLineNumber);
            return true;
        } else {
            log.info("Order Line does not exist: {}", orderLineNumber);
            return false;
        }
    }

    public boolean updateOrderLineShipInfo(OrderLine orderLine,
                                           String carrier,
                                           String packageNumber,
                                           Date shipDate) {
        orderLine.setCarrier(carrier);
        orderLine.setPackageNumber(packageNumber);
        orderLine.setShipDate(shipDate);
        orderLineRepository.save(orderLine);
        log.info("Updated Order Line: {}", orderLine);
        return true;
    }

    public boolean updateOrderLineState(OrderLine orderLine, OrderLineState state) {
        orderLine.setState(state);
        orderLineRepository.save(orderLine);
        log.info("Updated Order Line: {}", orderLine);
        return true;
    }

    public boolean updateOrderLineProductQty(OrderLine orderLine, int quantity) {
        if (quantity < 1) {
            log.warn("Quantity is less than one");
            return false;
        }

        orderLine.setQuantity(quantity);
        orderLineRepository.save(orderLine);
        log.info("Updated Order Line: {}", orderLine);
        return true;
    }

    public boolean updateOrderLineReceiptDate(OrderLine orderLine, Date receiptDate) {
        orderLine.setReceiptDate(receiptDate);
        orderLineRepository.save(orderLine);
        log.info("Updated Order Line: {}", orderLine);
        return true;
    }

    public List<OrderLine> findAllOrderLinesByOrderNumber(Long orderNumber) {
        List<OrderLine> orderLines = orderLineRepository
                .findByOrderHead_OrderNumberOrderByOrderLineNumberAsc(orderNumber);
        orderLines.forEach(orderLine -> log.info("Order Line Found: {} for OrderNumber: {}",
                orderLine, orderNumber));
        return orderLines;
    }

    public List<OrderLine> findAllOrderLinesByPackageNumber(String packageNumber) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("packageNumber", exact());
        List<OrderLine> orderLines = orderLineRepository.findAll(Example.of(
                OrderLine.builder().packageNumber(packageNumber).build(), exampleMatcher
        ));
        orderLines.forEach(orderLine -> log.info("Order Line Found: {} for PackageNumber: {}",
                orderLine, packageNumber));
        return orderLines;
    }

    public List<String> findPackageNumberByOrderNumber(Long orderNumber) {
        List<String> packageNumbers = orderLineRepository
                .findDistinctPackageNumberByOrderHead_OrderNumber(orderNumber);
        packageNumbers.forEach(packageNumber -> log.info("Package Number Found: {} for OrderNumber: {}",
                packageNumber, orderNumber));
        return packageNumbers;
    }

    public List<OrderLine> findOrderLinesByPackageNumber(String packageNumber) {
        List<OrderLine> orderLines = orderLineRepository
                .findByPackageNumberOrderByOrderLineNumberAsc(packageNumber);
        orderLines.forEach(orderLine -> log.info("Order Line Found: {} for PackageNumber: {}",
                orderLine, packageNumber));
        return orderLines;
    }
}

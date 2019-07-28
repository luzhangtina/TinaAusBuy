package com.tina.hashina.tinaausbuy.service;

import com.tina.hashina.tinaausbuy.module.*;
import com.tina.hashina.tinaausbuy.repository.OrderHeadRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderHeadService {
    @Autowired
    OrderHeadRepository orderHeadRepository;

    public OrderHead createOrderHead(Client client) {
        OrderHead orderHead = OrderHead.builder()
                .bookDate(new Date(System.currentTimeMillis()))
                .client(client)
                .state(OrderHeadState.INIT)
                .build();
        OrderHead savedOrderHead = orderHeadRepository.save(orderHead);
        log.info("New Order Head: {}", savedOrderHead);
        return savedOrderHead;
    }

    public boolean deleteOrderHead(Long orderNumber) {
        Optional<OrderHead> orderHead = orderHeadRepository.findById(orderNumber);
        if (orderHead.isPresent()) {
            log.info("Order Head is deleted: {}", orderHead.get());
            orderHeadRepository.deleteById(orderNumber);
            return true;
        } else {
            log.info("Order Head does not exist: {}", orderNumber);
            return false;
        }
    }

    public boolean updateOrderHeadPaymentAmount(OrderHead orderHead, double amount) {
        if (amount < orderHead.getProductsAmount().getAmountMinorInt()) {
            log.warn("Payment amount is less than the cost of products");
            return false;
        }

        orderHead.setPaymentAmount(Money.of(CurrencyUnit.of("CNY"), amount));
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean updateOrderHeadPostage(OrderHead orderHead, double amount) {
        if (amount <= 0) {
            log.warn("Postage is not larger than zero");
            return false;
        }

        orderHead.setPostage(Money.of(CurrencyUnit.of("CNY"), amount));
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean updateOrderHeadProductsAmount(OrderHead orderHead, double amount) {
        if (amount <= 0) {
            log.warn("Cost of products is not larger than zero");
            return false;
        }

        orderHead.setProductsAmount(Money.of(CurrencyUnit.of("CNY"), amount));
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean updateOrderHeadProfit(OrderHead orderHead, double amount) {
        if (amount < 0) {
            log.warn("Profit is less than zero");
            return false;
        }

        orderHead.setProfit(Money.of(CurrencyUnit.of("CNY"), amount));
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean updateOrderHeadCooperator(OrderHead orderHead, String cooperator) {
        orderHead.setCooperator(cooperator);
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean updateOrderHeadState(OrderHead orderHead, OrderHeadState state) {
        orderHead.setState(state);
        orderHeadRepository.save(orderHead);
        log.info("Updated Order Head: {}", orderHead);
        return true;
    }

    public boolean addOrderLine(OrderHead orderHead, OrderLine orderLine) {
        orderHead.addOrderLine(orderLine);
        return true;
    }

    public boolean deleteOrderLine(OrderHead orderHead, OrderLine orderLine) {
        orderHead.removeOrderLine(orderLine);
        return true;
    }

    public List<OrderHead> findAllOrdersByUserId(Long userId) {
        List<OrderHead> orderHeads = orderHeadRepository
                .findByClient_UserIdOrderByBookDateDesc(userId);
        orderHeads.forEach(orderHead -> log.info("Order Head Found: {} for UserId: {}",
                orderHead,
                userId));
        return orderHeads;
    }

    public List<OrderHead> findLatestTenOrders() {
        List<OrderHead> orderHeads = orderHeadRepository.findTop10ByOrderByBookDateDesc();
        orderHeads.forEach(orderHead -> log.info("Order Head Found: {}", orderHead));
        return orderHeads;
    }

    public List<OrderHead> findLatestThreeOrdersByUserId(Long userId) {
        List<OrderHead> orderHeads = orderHeadRepository
                .findTop3ByClient_UserIdOrderByBookDateDesc(userId);
        orderHeads.forEach(orderHead -> log.info("Order Head Found: {} for UserId: {}",
                orderHead,
                userId));
        return orderHeads;
    }

    public Optional<OrderHead> findOrderByOrderNumber(Long orderNumber) {
        Optional<OrderHead> orderHead = orderHeadRepository
                .findById(orderNumber);
        if (orderHead.isPresent()) {
            log.info("Order Head Found: {}", orderHead.get());
        }

        return orderHead;
    }
}

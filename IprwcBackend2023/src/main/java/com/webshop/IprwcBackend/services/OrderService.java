package com.webshop.IprwcBackend.services;

import com.sun.istack.NotNull;
import com.webshop.IprwcBackend.models.Order;
import com.webshop.IprwcBackend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrdersByUserId(Long id) {
        return this.orderRepository.findByUserId(id);
    }

    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        return this.orderRepository.save(order);
    }

    @NotNull
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}

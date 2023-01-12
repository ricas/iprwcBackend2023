package com.webshop.IprwcBackend.services;

import com.webshop.IprwcBackend.models.OrderProduct;
import com.webshop.IprwcBackend.repositories.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}

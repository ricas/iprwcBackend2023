package com.webshop.IprwcBackend.repositories;

import com.webshop.IprwcBackend.models.OrderProduct;
import com.webshop.IprwcBackend.models.compositekeys.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}

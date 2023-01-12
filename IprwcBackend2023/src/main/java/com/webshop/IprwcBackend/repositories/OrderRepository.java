package com.webshop.IprwcBackend.repositories;

import com.sun.istack.NotNull;
import com.webshop.IprwcBackend.models.Order;
import com.webshop.IprwcBackend.models.Product;
import com.webshop.IprwcBackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE user_id = :id ;", nativeQuery = true)
    List<Order> findByUserId(@Param("id")Long id);
}

package com.webshop.IprwcBackend.controllers;

import com.webshop.IprwcBackend.services.OrderService;
import com.webshop.IprwcBackend.services.OrderProductService;
import com.webshop.IprwcBackend.services.ProductService;
import com.webshop.IprwcBackend.dtos.OrderProductDTO;
import com.webshop.IprwcBackend.exceptions.ResourceNotFoundException;
import com.webshop.IprwcBackend.models.Order;

import com.webshop.IprwcBackend.models.OrderProduct;
import com.webshop.IprwcBackend.models.ShoppingCart;
import com.webshop.IprwcBackend.models.User;
import com.webshop.IprwcBackend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody ShoppingCart cartDTO) throws ResourceNotFoundException {
        List<OrderProductDTO> cart = cartDTO.getProductOrders();

        List<OrderProductDTO> list = cart
                .stream()
                .filter(op -> {
                    try {
                        return Objects.isNull(productService.getProduct(op
                                .getProduct()
                                .getId()));
                    } catch (ResourceNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("Product not found");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getUser(username);
        Order order = new Order();
        order.setUser(user);
        order = this.orderService.create(order);

        double totalPrice = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDTO dto : cart) {
            OrderProduct orderProduct = orderProductService.create(new OrderProduct(order, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity()));
            orderProducts.add(orderProduct);
            totalPrice += orderProduct.getTotalPrice();
        }

        order.setOrderProducts(orderProducts);
        order.setTotalPrice(totalPrice);

        return ResponseEntity.ok().body(this.orderService.create(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }
}

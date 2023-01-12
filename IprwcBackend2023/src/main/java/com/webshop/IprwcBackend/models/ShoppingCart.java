package com.webshop.IprwcBackend.models;

import com.webshop.IprwcBackend.dtos.OrderProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCart {
    private List<OrderProductDTO> productOrders;
}

package com.webshop.IprwcBackend.dtos;

import com.webshop.IprwcBackend.models.Product;
import lombok.Data;

@Data
public class OrderProductDTO {
    private Product product;
    private Integer quantity;
}

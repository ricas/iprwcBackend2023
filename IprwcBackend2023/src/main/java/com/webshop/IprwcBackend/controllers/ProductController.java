package com.webshop.IprwcBackend.controllers;

import com.sun.istack.NotNull;
import com.webshop.IprwcBackend.exceptions.ResourceNotFoundException;
import com.webshop.IprwcBackend.models.Product;
import com.webshop.IprwcBackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @NotNull
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @NotNull
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct (@RequestBody Product product) {
        return ResponseEntity.ok().body(this.productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}


package com.webshop.IprwcBackend.services;

import com.sun.istack.NotNull;
import com.webshop.IprwcBackend.exceptions.ResourceNotFoundException;
import com.webshop.IprwcBackend.models.Product;
import com.webshop.IprwcBackend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @NotNull
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product saveProduct(Product product) {
         return productRepository.save(product);
    }

    public Product updateProduct(Product newProduct, Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setImageUrl(newProduct.getImageUrl());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

}

package com.oxtore.product.service;

import com.oxtore.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void deleteById(Long id);

}

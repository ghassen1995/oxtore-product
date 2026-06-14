package com.oxtore.product.service;

import com.oxtore.product.entities.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();

}

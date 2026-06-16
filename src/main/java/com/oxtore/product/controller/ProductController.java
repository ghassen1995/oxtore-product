package com.oxtore.product.controller;

import com.oxtore.product.DTOs.CreateProductRequest;
import com.oxtore.product.DTOs.ProductResponse;
import com.oxtore.product.entities.Product;
import com.oxtore.product.mapper.ProductMapper;
import com.oxtore.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productMapper.productRequestToProduct(createProductRequest);
        product.getWholesalePriceTiers().forEach(tier -> tier.setProduct(product));
        if(createProductRequest.commissionRule() != null) {
            product.getCommissionRule().setProduct(product);
        }
        productService.save(product);
        return ResponseEntity.ok(productMapper.productToProductResponse(product));
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductResponse> productResponses = products.stream().map(productMapper::productToProductResponse).toList();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.findById(id).map(
                product -> ResponseEntity.ok(
                        productMapper.productToProductResponse(product))).orElse(ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

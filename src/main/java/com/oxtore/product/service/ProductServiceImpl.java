package com.oxtore.product.service;

import com.oxtore.product.entities.Product;
import com.oxtore.product.enums.SaleType;
import com.oxtore.product.enums.TransactionMode;
import com.oxtore.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getSaleType() != SaleType.WHOLESALE && product.getMarketPrice() != null) {
            throw new RuntimeException("Market price is not allowed, sale type must be WHOLESALE");
        }

        if (product.getCommissionRule() != null) {
            boolean isAllowed = product.getSaleType() == SaleType.WHOLESALE
                    && product.getTransactionMode() == TransactionMode.CONSIGNMENT;

            if (!isAllowed) {
                throw new RuntimeException(
                        "Commission rule is not allowed. It is only permitted when SaleType=WHOLESALE and TransactionMode=CONSIGNMENT"
                );
            }
        }

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

package com.oxtore.product.service;

import com.oxtore.product.DTOs.ProductCreatedEvent;
import com.oxtore.product.entities.Product;
import com.oxtore.product.enums.SaleType;
import com.oxtore.product.enums.TransactionMode;
import com.oxtore.product.exceptions.CommissionRuleNotAllowedException;
import com.oxtore.product.exceptions.MarketPriceNotAllowedException;
import com.oxtore.product.exceptions.WholeSaleIsEmptyException;
import com.oxtore.product.exceptions.WholeSaleIsNotAllowedException;
import com.oxtore.product.kafka.ProductEventPublisher;
import com.oxtore.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductEventPublisher productEventPublisher;

    public ProductServiceImpl(ProductRepository productRepository, ProductEventPublisher productEventPublisher) {
        this.productRepository = productRepository;
        this.productEventPublisher = productEventPublisher;
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getSaleType() != SaleType.WHOLESALE && product.getMarketPrice() != null) {
            throw new MarketPriceNotAllowedException();
        }

        if (product.getCommissionRule() != null) {
            boolean isAllowed = product.getSaleType() == SaleType.WHOLESALE
                    && product.getTransactionMode() == TransactionMode.CONSIGNMENT;

            if (!isAllowed) {
                throw new CommissionRuleNotAllowedException();
            }
        }

        if (product.getSaleType() == SaleType.WHOLESALE && product.getWholesalePriceTiers().isEmpty()) {
            throw new WholeSaleIsEmptyException();
        }

        if (product.getSaleType() == SaleType.RETAIL && !product.getWholesalePriceTiers().isEmpty()) {
            throw new WholeSaleIsNotAllowedException();
        }

        Product savedProduct = productRepository.save(product);

        productEventPublisher.publishProductCreated(new ProductCreatedEvent(savedProduct.getId(), savedProduct.getStoreId(), savedProduct.getAvailableStock(), savedProduct.getSaleType(), savedProduct.getTransactionMode(), savedProduct.getCreatedAt()));
        return savedProduct;
    }

    @Transactional(readOnly = true)
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

package com.oxtore.product.DTOs;

import com.oxtore.product.enums.SaleType;
import com.oxtore.product.enums.TransactionMode;

import java.time.LocalDateTime;

public record ProductCreatedEvent(
        Long productId,
        Long storeId,
        Integer initialStock,
        SaleType saleType,
        TransactionMode transactionMode,
        LocalDateTime occurredAt
) {}
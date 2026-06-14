package com.oxtore.product.DTOs;

import com.oxtore.product.enums.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        ProductCondition condition,
        SaleType saleType,
        TransactionMode transactionMode,
        String productName,
        BigDecimal marketPrice,
        BigDecimal retailPrice,
        Integer availableStock,
        UnitOfSale unitOfSale,
        Long commissionRuleId,
        List<ProductImageRequest> images,
        List<WholesalePriceTierRequest> wholesalePriceTiers,
        ProductStatus status
) {
}

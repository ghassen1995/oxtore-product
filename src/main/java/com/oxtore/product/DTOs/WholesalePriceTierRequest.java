package com.oxtore.product.DTOs;

import java.math.BigDecimal;

public record WholesalePriceTierRequest(
        Integer minQuantity,
        BigDecimal unitPrice
) {
}

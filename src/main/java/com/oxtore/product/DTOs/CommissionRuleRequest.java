package com.oxtore.product.DTOs;

import java.math.BigDecimal;

public record CommissionRuleRequest(
        BigDecimal levelOneCommission,
        BigDecimal levelTwoCommission,
        BigDecimal levelThreeCommission) {
}

package com.oxtore.product.DTOs;

public record ProductImageResponse(
        Long id,
        String url,
        Integer displayOrder
) {
}

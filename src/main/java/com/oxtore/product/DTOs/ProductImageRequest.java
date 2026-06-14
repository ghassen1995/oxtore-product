package com.oxtore.product.DTOs;

public record ProductImageRequest(
        Long id,
        String url,
        Integer displayOrder
) {
}

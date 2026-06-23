package com.oxtore.product.mapper;

import com.oxtore.product.DTOs.CreateProductRequest;
import com.oxtore.product.DTOs.ProductCreatedEvent;
import com.oxtore.product.DTOs.ProductResponse;
import com.oxtore.product.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse productToProductResponse(Product product);

    Product productRequestToProduct(CreateProductRequest productRequest);

    ProductCreatedEvent productToProductCreatedEvent(Product product);
}

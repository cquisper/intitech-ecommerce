package com.cquisper.msvc.order.dto;

import com.cquisper.msvc.order.models.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        Long id,
        Product product,
        String color,
        Integer quantity,
        BigDecimal price
) {
}

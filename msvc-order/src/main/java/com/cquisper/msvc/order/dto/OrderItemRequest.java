package com.cquisper.msvc.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemRequest(
        String product,
        String color,
        Integer quantity,
        BigDecimal price
) {
}

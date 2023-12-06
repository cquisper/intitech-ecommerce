package com.cquisper.msvc.cart.dto;

import com.cquisper.msvc.cart.models.Image;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CartItemsResponse(
        Long id,
        String productId,
        String name,
        String category,
        Integer count,
        String color,
        BigDecimal price,
        List<Image> images
) {
}

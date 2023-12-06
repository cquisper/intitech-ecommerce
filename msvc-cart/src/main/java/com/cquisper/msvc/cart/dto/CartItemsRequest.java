package com.cquisper.msvc.cart.dto;

import lombok.Builder;

@Builder
public record CartItemsRequest(
        String productId,
        Integer count,
        String color
) {
}

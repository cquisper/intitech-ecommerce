package com.cquisper.msvc.cart.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CartRequest(
        List<CartItemsRequest> cart
) {
}

package com.cquisper.msvc.cart.dto;

import com.cquisper.msvc.cart.models.User;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CartResponse(
        Long id,
        List<CartItemsResponse> products,
        BigDecimal cartTotal,
        BigDecimal totalAfterDiscount,
        User orderBy,
        LocalDateTime createAt
) {
}

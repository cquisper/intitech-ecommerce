package com.cquisper.msvc.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CheckoutDto(
        BigDecimal amount
) {
}

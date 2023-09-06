package com.cquisper.msvc.msvc.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineItemsDto(Long id, String productCode, BigDecimal price, Integer quantity) {
}

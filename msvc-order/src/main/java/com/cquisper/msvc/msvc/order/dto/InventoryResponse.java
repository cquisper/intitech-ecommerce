package com.cquisper.msvc.msvc.order.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(
        String productCode,
        Integer availableQuantity,
        Boolean isInStock
) {
}

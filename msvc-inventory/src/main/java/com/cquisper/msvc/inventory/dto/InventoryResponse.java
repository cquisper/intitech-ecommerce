package com.cquisper.msvc.inventory.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(
        String productCode,
        Integer availableQuantity,
        Boolean isInStock
) {
}

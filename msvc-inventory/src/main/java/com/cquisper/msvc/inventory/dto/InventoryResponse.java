package com.cquisper.msvc.inventory.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(
        Long id,
        String productId,
        Integer quantity,
        Boolean isInStock,
        Integer sold
) {
}

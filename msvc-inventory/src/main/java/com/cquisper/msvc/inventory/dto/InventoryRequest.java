package com.cquisper.msvc.inventory.dto;

public record InventoryRequest(
        String productId,
        Integer quantity
) {
}
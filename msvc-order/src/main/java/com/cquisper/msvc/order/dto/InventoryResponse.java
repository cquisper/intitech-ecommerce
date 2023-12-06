package com.cquisper.msvc.order.dto;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data @Builder
public class InventoryResponse{
    private Long id;

    private String productId;

    private Integer quantity;

    private Boolean isInStock;

    private Integer sold;

    @ConstructorProperties({"id", "productId", "quantity", "isInStock", "sold"})
    public InventoryResponse(Long id, String productId, Integer quantity, Boolean isInStock, Integer sold) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.isInStock = isInStock;
        this.sold = sold;
    }
}
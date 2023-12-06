package com.cquisper.msvc.products.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data @Builder
public class Inventory {
    private Long id;

    private String productId;

    private Integer quantity;

    private Boolean isInStock;

    private Integer sold;

    @ConstructorProperties({"id", "productId", "quantity", "isInStock", "sold"})
    public Inventory(Long id, String productId, Integer quantity, Boolean isInStock, Integer sold) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.isInStock = isInStock;
        this.sold = sold;
    }
}

package com.cquisper.msvc.order.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Data @Builder
public class CartItem {
    private Long id;

    private String productId;

    private String name;

    private String category;

    private Integer count;

    private String color;

    private BigDecimal price;

    @ConstructorProperties({"id", "productId", "name", "category" ,"count", "color", "price"})
    public CartItem(Long id, String productId, String name, String category , Integer count, String color, BigDecimal price) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.count = count;
        this.color = color;
        this.price = price;
    }
}

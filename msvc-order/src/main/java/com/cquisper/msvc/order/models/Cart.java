package com.cquisper.msvc.order.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class Cart {
    private Long id;

    private List<CartItem> products;

    private BigDecimal cartTotal;

    private BigDecimal totalAfterDiscount;

    private User orderBy;

    private LocalDateTime createAt;

    @ConstructorProperties({"id", "products", "cartTotal", "totalAfterDiscount", "orderBy", "createAt"})
    public Cart(Long id, List<CartItem> products, BigDecimal cartTotal,
                BigDecimal totalAfterDiscount, User orderBy, LocalDateTime createAt) {
        this.id = id;
        this.products = products;
        this.cartTotal = cartTotal;
        this.totalAfterDiscount = totalAfterDiscount;
        this.orderBy = orderBy;
        this.createAt = createAt;
    }
}

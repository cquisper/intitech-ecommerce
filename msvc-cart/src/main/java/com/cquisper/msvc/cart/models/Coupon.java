package com.cquisper.msvc.cart.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class Coupon {

    private String id;

    private String code;

    private BigDecimal discount;

    private LocalDateTime expiryDate;

    @ConstructorProperties({"id", "code", "discount", "expiryDate"})
    public Coupon(String id, String code, BigDecimal discount, LocalDateTime expiryDate) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.expiryDate = expiryDate;
    }
}

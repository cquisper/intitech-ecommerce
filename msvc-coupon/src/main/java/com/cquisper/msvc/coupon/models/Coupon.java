package com.cquisper.msvc.coupon.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "coupons")
@Data @Builder
public class Coupon {

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    private BigDecimal discount;

    private LocalDateTime expiryDate;
}

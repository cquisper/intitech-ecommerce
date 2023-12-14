package com.cquisper.msvc.order.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String user;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private ShippingInfo shippingInfo;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private PaymentInfo paymentInfo;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private LocalDateTime paidAt;

    private BigDecimal totalPrice;

    private BigDecimal totalPriceAfterDiscount;

    private String orderStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
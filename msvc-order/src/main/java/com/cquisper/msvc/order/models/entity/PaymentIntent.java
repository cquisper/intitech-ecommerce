package com.cquisper.msvc.order.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_intent")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private BigDecimal amount;

    private String status;

    private LocalDateTime created;

    private String currency;
}

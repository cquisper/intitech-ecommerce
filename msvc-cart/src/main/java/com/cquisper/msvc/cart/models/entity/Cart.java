package com.cquisper.msvc.cart.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItems> cartItems;

    private BigDecimal cartTotal;

    private BigDecimal totalAfterDiscount;

    private Long orderBy;

    private LocalDateTime createAt;
}

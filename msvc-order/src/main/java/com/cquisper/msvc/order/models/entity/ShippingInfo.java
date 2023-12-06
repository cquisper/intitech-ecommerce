package com.cquisper.msvc.order.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipping_info")
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class ShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String state;

    private String other;

    private Integer pinCode;
}

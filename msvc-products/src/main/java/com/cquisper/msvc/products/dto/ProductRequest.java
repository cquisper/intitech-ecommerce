package com.cquisper.msvc.products.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(String name, String description, BigDecimal price) {
}
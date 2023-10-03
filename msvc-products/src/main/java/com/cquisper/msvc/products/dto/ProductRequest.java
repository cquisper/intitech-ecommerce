package com.cquisper.msvc.products.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductRequest(
        String name,
        String sku,
        String description,
        String category,
        BigDecimal price,
        List<String> colors,
        String brand,
        String tags,
        List<String> images)
{
}
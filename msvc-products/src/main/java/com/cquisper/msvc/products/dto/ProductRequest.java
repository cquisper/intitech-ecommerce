package com.cquisper.msvc.products.dto;

import com.cquisper.msvc.products.models.entities.Image;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductRequest(
        String name,
        String description,
        String category,
        Double price,
        List<String> colors,
        String brand,
        String tags,
        List<Image> images,
        Integer quantity)
{
}
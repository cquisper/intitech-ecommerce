package com.cquisper.msvc.products.dto;

import com.cquisper.msvc.products.models.Rating;
import com.cquisper.msvc.products.models.enums.Color;
import com.cquisper.msvc.products.models.enums.Image;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductResponse(
        String id,
        String sku,
        String name,
        String description,
        String category,
        BigDecimal price,
        List<String> colors,
        String brand,
        String tags,
        List<String> images,
        List<Rating> ratings,
        BigDecimal totalRating
)
{
}

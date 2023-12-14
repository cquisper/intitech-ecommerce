package com.cquisper.msvc.products.dto;

import com.cquisper.msvc.products.models.Rating;
import com.cquisper.msvc.products.models.entities.Image;
import lombok.Builder;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductResponse(
        String id,
        String sku,
        String name,
        String description,
        String category,
        Double price,
        List<String> colors,
        String brand,
        String tags,
        List<Image> images,
        List<Rating> ratings,
        Double totalRating,
        Integer quantity,
        Integer sold,
        LocalDateTime createdAt
)
{
}

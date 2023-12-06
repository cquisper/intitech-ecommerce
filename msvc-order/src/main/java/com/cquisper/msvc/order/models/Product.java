package com.cquisper.msvc.order.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class Product {
    private String sku;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private List<String> colors;

    private String brand;

    private String tags;

    private List<Image> images;

    private BigDecimal totalRating;

    private LocalDateTime createdAt;

    @ConstructorProperties({"sku", "name", "description", "category", "price", "colors", "brand", "tags", "images", "totalRating", "createdAt"})
    public Product(String sku, String name, String description, String category, BigDecimal price, List<String> colors, String brand, String tags, List<Image> images, BigDecimal totalRating, LocalDateTime createdAt) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.colors = colors;
        this.brand = brand;
        this.tags = tags;
        this.images = images;
        this.totalRating = totalRating;
        this.createdAt = createdAt;
    }
}

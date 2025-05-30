package com.cquisper.msvc.products.models.entities;

import com.cquisper.msvc.products.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document("products")
@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class Product {
    @Id
    private String id;

    @Indexed(unique = true)
    private String sku;

    private String name;

    private String description;

    private String category;

    private Double price;

    private List<String> colors;

    private String brand;

    private String tags;

    private List<Image> images;

    @Transient
    private List<Rating> ratings;

    private Double totalRating;

    private LocalDateTime createdAt;
}

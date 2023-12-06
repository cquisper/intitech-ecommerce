package com.cquisper.msvc.cart.models;

import lombok.Data;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.List;

//@Builder
@Data
public class Product {

    private String id;

    private String name;

    private String category;

    private BigDecimal price;

    private List<Image> images;
    @ConstructorProperties({"id", "price", "name", "category", "images"})
    public Product(String id, BigDecimal price, String name, String category, List<Image> images) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.category = category;
        this.images = images;
    }
}

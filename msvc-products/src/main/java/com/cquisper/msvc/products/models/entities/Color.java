package com.cquisper.msvc.products.models.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colors")
@Data @Builder
public class Color {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
}
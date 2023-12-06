package com.cquisper.msvc.products.models.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brands")
@Builder @Data
public class Brand {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
}

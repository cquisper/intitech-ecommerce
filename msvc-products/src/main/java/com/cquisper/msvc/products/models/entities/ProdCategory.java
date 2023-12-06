package com.cquisper.msvc.products.models.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prod_categories")
@Data @Builder
public class ProdCategory {

    @Id
    private String id;

    private String name;
}

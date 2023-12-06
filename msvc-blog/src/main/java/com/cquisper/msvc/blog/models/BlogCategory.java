package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blog_categories")
@Data @Builder
public class BlogCategory {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
}

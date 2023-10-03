package com.cquisper.msvc.ratings.models.entities;

import com.cquisper.msvc.ratings.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ratings")
@Builder @Data
@AllArgsConstructor @NoArgsConstructor
public class Rating {
    @Id
    private String id;

    private Integer star;

    private String comment;

    private String idProduct;

    private User user;
}

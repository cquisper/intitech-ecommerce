package com.cquisper.msvc.products.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Builder @Data
public class Rating {

    private String id;

    private Integer star;

    private String comment;

    private User user;

    @ConstructorProperties({"id", "star", "comment", "user"})
    public Rating(String id, Integer star, String comment, User user){
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.user = user;
    }
}

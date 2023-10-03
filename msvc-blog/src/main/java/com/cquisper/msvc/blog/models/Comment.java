package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class Comment {
    private String id;

    private User author;

    private String commentText;

    private Integer numLikes;

    private List<Reply> replies;

    // Otros atributos que puedas necesitar
}
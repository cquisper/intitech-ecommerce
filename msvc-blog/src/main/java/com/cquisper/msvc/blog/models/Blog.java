package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document("blogs")
@Data @Builder
public class Blog {
    @Id
    private String id;

    private String title;

    private String description;

    private String category;

    private User author;

    private Integer numViews;

    private Integer numComments;

    private Integer numLikes;

    private Set<User> likeUsers;

    private List<Comment> comments;

    private String image;
}

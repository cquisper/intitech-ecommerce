package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Reply {
    private String id;

    private User author;

    private User replyTo;

    private String replyText;

    private Integer numLikes;
}

package com.cquisper.msvc.blog.dto;

public record CommentRequest(
        String blogId,
        String commentText
) {
}

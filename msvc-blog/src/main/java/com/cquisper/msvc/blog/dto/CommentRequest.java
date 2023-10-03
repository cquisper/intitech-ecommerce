package com.cquisper.msvc.blog.dto;

public record CommentRequest(
        String blogId,
        Long userId,
        String commentText
) {
}

package com.cquisper.msvc.blog.dto;

public record ReplyRequest(
        String blogId,
        Long userId,
        String commentId,
        String replyText,
        Long replyToId
) {
}

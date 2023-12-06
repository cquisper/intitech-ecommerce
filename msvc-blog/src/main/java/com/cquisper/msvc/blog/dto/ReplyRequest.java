package com.cquisper.msvc.blog.dto;

public record ReplyRequest(
        String blogId,
        String commentId,
        String replyText,
        Long replyToId
) {
}

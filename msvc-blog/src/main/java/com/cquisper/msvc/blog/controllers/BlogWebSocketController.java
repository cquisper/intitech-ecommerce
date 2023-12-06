package com.cquisper.msvc.blog.controllers;

import com.cquisper.msvc.blog.dto.BlogResponse;
import com.cquisper.msvc.blog.dto.CommentRequest;
import com.cquisper.msvc.blog.dto.ReplyRequest;
import com.cquisper.msvc.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class BlogWebSocketController {

    private final BlogService blogService;

    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public Mono<BlogResponse> commentToBlog(CommentRequest commentRequest, @RequestHeader String email) {
        return this.blogService.commentToBlog(commentRequest, email);
    }

    @MessageMapping("/reply")
    @SendTo("/topic/replies")
    public Mono<BlogResponse> replyToCommentBlog(ReplyRequest replyRequest, @RequestHeader String email) {
        return this.blogService.replyToCommentBlog(replyRequest, email);
    }
}

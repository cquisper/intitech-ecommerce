package com.cquisper.msvc.blog.controllers;

import com.cquisper.msvc.blog.services.BlogService;
import com.cquisper.msvc.blog.dto.BlogRequest;
import com.cquisper.msvc.blog.dto.BlogResponse;
import com.cquisper.msvc.blog.dto.CommentRequest;
import com.cquisper.msvc.blog.dto.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/all")
    @ResponseStatus(OK)
    public Flux<BlogResponse> getAllBlogs() {
        return this.blogService.getAllBlogs();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(OK)
    public Mono<BlogResponse> getByIdBlog(@PathVariable String id) {
        return this.blogService.getByIdBlog(id);
    }

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public Mono<BlogResponse> createBlog(@RequestBody BlogRequest blogRequest) {
        return this.blogService.createBlog(blogRequest);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(OK)
    public Mono<BlogResponse> updateBlog(@RequestBody Map<Object, Object> fields, @PathVariable String id) {
        return this.blogService.updateBlog(fields, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return this.blogService.deleteById(id);
    }

    @PutMapping("/like/{idBlog}/{idUser}")
    @ResponseStatus(OK)
    public Mono<BlogResponse> giveLikeToBlog(@PathVariable String idBlog, @PathVariable Long idUser) {
        return this.blogService.giveLikeToBlog(idBlog, idUser);
    }

    @PostMapping("/comment")
    @ResponseStatus(CREATED)
    public Mono<BlogResponse> commentToBlog(@RequestBody CommentRequest commentText) {
        return this.blogService.commentToBlog(commentText);
    }

    @PostMapping("/reply")
    @ResponseStatus(CREATED)
    public Mono<BlogResponse> replyToCommentBlog(@RequestBody ReplyRequest replyRequest) {
        return this.blogService.replyToCommentBlog(replyRequest);
    }
}

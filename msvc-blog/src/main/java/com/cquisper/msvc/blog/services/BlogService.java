package com.cquisper.msvc.blog.services;

import com.cquisper.msvc.blog.client.UserWebClient;
import com.cquisper.msvc.blog.dto.BlogRequest;
import com.cquisper.msvc.blog.dto.BlogResponse;
import com.cquisper.msvc.blog.dto.CommentRequest;
import com.cquisper.msvc.blog.dto.ReplyRequest;
import com.cquisper.msvc.blog.models.Blog;
import com.cquisper.msvc.blog.models.Comment;
import com.cquisper.msvc.blog.models.Reply;
import com.cquisper.msvc.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    private final UserWebClient userWebClient;

    public Flux<BlogResponse> getAllBlogs() {
        return this.blogRepository.findAll()
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> getByIdBlog(String id) {
        return blogRepository.findById(id)
                .doOnNext(blog -> blog.setNumViews(blog.getNumViews() + 1))
                .flatMap(this.blogRepository::save)
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> createBlog(BlogRequest blogRequest) {
        return this.blogRepository.insert(dtoToEntity(blogRequest))
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> updateBlog(Map<Object, Object> fields, String id){
        return this.blogRepository.findById(id)
                .doOnNext(blog -> {
                    fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findRequiredField(Blog.class, key.toString());
                        field.setAccessible(true);
                        log.info("Modification: " + field.getName());
                        ReflectionUtils.setField(field, blog, value);
                    });
                })
                .flatMap(this.blogRepository::save)
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> giveLikeToBlog(String idBlog, Long idUser){
        return this.blogRepository.findById(idBlog)
                .flatMap(blog -> this.userWebClient.getUserById(idUser)
                        .doOnNext(user -> {
                            if (blog.getLikeUsers().add(user)) {
                                blog.setNumLikes(blog.getNumLikes() + 1);
                            }else{
                                blog.setNumLikes(blog.getNumLikes() - 1);
                                blog.getLikeUsers().remove(user);
                            }
                        })
                        .thenReturn(blog)
                )
                .flatMap(this.blogRepository::save)
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> commentToBlog(CommentRequest commentRequest){
        return this.blogRepository.findById(commentRequest.blogId())
                .switchIfEmpty(Mono.error(new RuntimeException("Blog not found")))
                .flatMap(blog -> this.userWebClient.getUserById(commentRequest.userId())
                        .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                        .doOnNext(user -> {
                            Comment comment = Comment.builder()
                                    .id(UUID.randomUUID().toString())
                                    .commentText(commentRequest.commentText())
                                    .author(user)
                                    .replies(List.of())
                                    .numLikes(0)
                                    .build();
                            blog.getComments().add(comment);
                            blog.setNumComments(blog.getNumComments() + 1);
                        })
                        .thenReturn(blog)
                )
                .flatMap(this.blogRepository::save)
                .map(BlogService::entityToDto);
    }

    public Mono<BlogResponse> replyToCommentBlog(ReplyRequest replyRequest){
        return this.blogRepository.findById(replyRequest.blogId())
                .switchIfEmpty(Mono.error(new RuntimeException("Blog not found")))
                .flatMap(blog -> commentFindById(blog.getComments(), replyRequest.commentId())
                    .filter(comment -> comment.getId().equals(replyRequest.commentId()))
                    .switchIfEmpty(Mono.error(new RuntimeException("Comment not found")))
                    .flatMap(comment -> this.userWebClient.getUserById(replyRequest.userId())
                            .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                            .flatMap(user -> {
                                Reply reply = Reply.builder()
                                        .id(UUID.randomUUID().toString())
                                        .author(user)
                                        .replyText(replyRequest.replyText())
                                        .numLikes(0)
                                        .build();
                                if (comment.getAuthor().getId().equals(replyRequest.replyToId())) {
                                    reply.setReplyTo(comment.getAuthor());
                                }else {
                                    return this.userWebClient.getUserById(replyRequest.replyToId())
                                            .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                                            .doOnNext(reply::setReplyTo)
                                            .thenReturn(reply);
                                }
                                return Mono.just(reply);
                            })
                            .doOnNext(reply -> {
                                comment.getReplies().add(reply);
                            })
                            .thenReturn(comment)
                    ).doOnNext(comment -> {
                        blog.setNumComments(blog.getNumComments() + 1);
                    }).thenReturn(blog)
                )
                .flatMap(this.blogRepository::save)
                .map(BlogService::entityToDto);

    }

    public Mono<Comment> commentFindById(List<Comment> comments, String idComment){
        return Flux.fromIterable(comments)
                .filter(comment -> comment.getId().equals(idComment))
                .switchIfEmpty(Mono.error(new RuntimeException("Comment not found")))
                .next();
    }

    public Mono<Void> deleteById(String id) {
        return this.blogRepository.deleteById(id);
    }

    public static Blog dtoToEntity(BlogRequest blogRequest) {
        return Blog.builder()
                .title(blogRequest.title())
                .description(blogRequest.description())
                .category(blogRequest.category())
                .numViews(0)
                .numComments(0)
                .numLikes(0)
                .likeUsers(Set.of())
                .comments(List.of())
                .build();
    }

    public static BlogResponse entityToDto(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .category(blog.getCategory())
                .numViews(blog.getNumViews())
                .numComments(blog.getNumComments())
                .likeUsers(blog.getLikeUsers())
                .numLikes(blog.getNumLikes())
                .comments(blog.getComments())
                .build();
    }
}

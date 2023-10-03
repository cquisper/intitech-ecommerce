package com.cquisper.msvc.blog.repository;

import com.cquisper.msvc.blog.models.Blog;
import com.cquisper.msvc.blog.models.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
}

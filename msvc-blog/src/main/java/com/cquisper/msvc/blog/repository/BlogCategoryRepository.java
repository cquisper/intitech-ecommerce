package com.cquisper.msvc.blog.repository;

import com.cquisper.msvc.blog.models.BlogCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BlogCategoryRepository extends ReactiveMongoRepository<BlogCategory, String> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
}

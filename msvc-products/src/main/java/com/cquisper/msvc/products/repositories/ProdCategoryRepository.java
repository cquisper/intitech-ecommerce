package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.ProdCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProdCategoryRepository extends ReactiveMongoRepository<ProdCategory, String> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
}

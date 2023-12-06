package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.Brand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BrandRepository extends ReactiveMongoRepository<Brand, String> {
    Mono<Boolean> existsByNameIgnoreCase(String name);
}

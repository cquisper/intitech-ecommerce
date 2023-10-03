package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Boolean> existsBySku(String sku);
}

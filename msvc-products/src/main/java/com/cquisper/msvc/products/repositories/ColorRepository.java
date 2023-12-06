package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.Color;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ColorRepository extends ReactiveMongoRepository<Color, String>{
    Mono<Boolean> existsByNameIgnoreCase(String name);
}

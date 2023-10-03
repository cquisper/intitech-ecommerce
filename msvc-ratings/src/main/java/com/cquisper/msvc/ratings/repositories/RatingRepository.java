package com.cquisper.msvc.ratings.repositories;

import com.cquisper.msvc.ratings.models.User;
import com.cquisper.msvc.ratings.models.entities.Rating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {
    Flux<Rating> findAllByIdProduct(String idProduct);
    Mono<Rating> findByUserAndIdProduct(User user, String idProduct);
    Mono<Long> countByIdProduct(String idProduct);
}

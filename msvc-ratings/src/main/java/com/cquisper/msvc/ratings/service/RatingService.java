package com.cquisper.msvc.ratings.service;

import com.cquisper.msvc.ratings.client.ProductWebClient;
import com.cquisper.msvc.ratings.client.UserWebClient;
import com.cquisper.msvc.ratings.dto.RatingResponse;
import com.cquisper.msvc.ratings.dto.RatingResquest;
import com.cquisper.msvc.ratings.models.entities.Rating;
import com.cquisper.msvc.ratings.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    private final UserWebClient userWebClient;

    private final ProductWebClient productWebClient;

    public Flux<RatingResponse> getAllRatingByIdProduct(String idProduct){
        return this.ratingRepository.findAllByIdProduct(idProduct)
                .map(RatingService::entityToDto);
    }

    public Mono<RatingResponse> ratingToProduct(RatingResquest ratingRequest){
        return this.userWebClient.getUserById(ratingRequest.idUser())
                .flatMap(user -> this.ratingRepository.findByUserAndIdProduct(user, ratingRequest.idProduct())
                        .doOnNext(rating -> {
                            rating.setStar(ratingRequest.star());
                            rating.setComment(ratingRequest.comment());
                        })
                        .flatMap(this.ratingRepository::save)
                        .doOnSuccess(rating -> log.info("Rating updated: {}", rating))
                        .switchIfEmpty(Mono.defer(() -> Mono.just(dtoToEntity(ratingRequest))
                                .doOnNext(rating -> rating.setUser(user))
                                .flatMap(this.ratingRepository::insert))
                                .doOnSuccess(rating -> log.info("Rating insert: {}", rating))
                        )
                        .flatMap(rating -> this.ratingRepository.findAllByIdProduct(rating.getIdProduct())
                                .map(Rating::getStar)
                                .reduce(Integer::sum)
                                .flatMap(ratingSum -> this.ratingRepository.countByIdProduct(rating.getIdProduct())
                                        .flatMap(totalRating -> this.productWebClient.updateProduct(
                                                BigDecimal.valueOf((double) ratingSum / totalRating), //totalRating
                                                rating.getIdProduct())//idProduct
                                        )
                                )
                                .thenReturn(entityToDto(rating))
                        )
                );
    }

    public static Rating dtoToEntity(RatingResquest ratingRequest){
        return Rating.builder()
                .star(ratingRequest.star())
                .comment(ratingRequest.comment())
                .idProduct(ratingRequest.idProduct())
                .build();
    }

    public static RatingResponse entityToDto(Rating rating){
        return RatingResponse.builder()
                .id(rating.getId())
                .star(rating.getStar())
                .comment(rating.getComment())
                .user(rating.getUser())
                .build();
    }
}
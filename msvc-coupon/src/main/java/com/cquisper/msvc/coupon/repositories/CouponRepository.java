package com.cquisper.msvc.coupon.repositories;

import com.cquisper.msvc.coupon.models.Coupon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CouponRepository extends ReactiveMongoRepository<Coupon, String> {
    Mono<Coupon> findByCode(String code);
}

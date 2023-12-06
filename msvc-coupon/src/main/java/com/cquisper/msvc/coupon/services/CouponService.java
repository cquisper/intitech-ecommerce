package com.cquisper.msvc.coupon.services;

import com.cquisper.msvc.coupon.models.Coupon;
import com.cquisper.msvc.coupon.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service @Slf4j
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Flux<Coupon> getAllCoupons() {
        return this.couponRepository.findAll();
    }

    public Mono<Coupon> createCoupon(Coupon coupon) {
        return Mono.just(coupon)
                .doOnNext(c -> c.setCode(c.getCode().toUpperCase()))
                .flatMap(this.couponRepository::save);
    }

    public Mono<Coupon> getCouponById(String id) {
        return this.couponRepository.findById(id);
    }

    public Mono<Coupon> getCouponByCode(String code) {
        return this.couponRepository.findByCode(code);
    }

    public Mono<Coupon> updateCoupon(Map<String, Object> fields, String id){
        return this.couponRepository.findById(id)
                .doOnNext(coupon -> {
                    fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findRequiredField(Coupon.class, key);
                        field.setAccessible(true);
                        log.info("Updating field {} to {}", key, value);
                        switch (value.getClass().getSimpleName()) {
                            case "Integer" -> ReflectionUtils.setField(field, coupon, BigDecimal.valueOf((Integer) value));
                            case "Double" -> ReflectionUtils.setField(field, coupon, BigDecimal.valueOf((Double) value));
                            default -> {
                                if(key.equals("expiryDate")){
                                    ReflectionUtils.setField(field, coupon, LocalDateTime.parse(value.toString()));
                                    return;
                                }
                                ReflectionUtils.setField(field, coupon, value);
                            }
                        }
                    });
                    coupon.setCode(coupon.getCode().toUpperCase());
                })
                .flatMap(this.couponRepository::save)
                .doOnSuccess(c -> log.info("Coupon {} updated", c));
    }

    public Mono<Void> deleteCoupon(String id) {
        return this.couponRepository.deleteById(id)
                .doOnSuccess(c -> log.info("Coupon {} deleted", id));
    }
}

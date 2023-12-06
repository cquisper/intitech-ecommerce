package com.cquisper.msvc.coupon.controller;

import com.cquisper.msvc.coupon.models.Coupon;
import com.cquisper.msvc.coupon.services.CouponService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/all")
    @ResponseStatus(OK)
    public Flux<Coupon> getAllCoupons() {
        return this.couponService.getAllCoupons();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(OK)
    public Mono<Coupon> getCouponById(@PathVariable String id) {
        return this.couponService.getCouponById(id);
    }

    @GetMapping("/find-by-code/{code}")
    @ResponseStatus(OK)
    public Mono<Coupon> getCouponByCode(@PathVariable String code) {
        return this.couponService.getCouponByCode(code);
    }

    @PostMapping("/create")
    @ResponseStatus(OK)
    public Mono<Coupon> createCoupon(@RequestBody Coupon coupon) {
        return this.couponService.createCoupon(coupon);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(CREATED)
    public Mono<Coupon> updateCoupon(@RequestBody Map<String, Object> fields, @PathVariable String id){
        return this.couponService.updateCoupon(fields, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteCoupon(@PathVariable String id) {
        return this.couponService.deleteCoupon(id);
    }
}

package com.cquisper.msvc.cart.client;

import com.cquisper.msvc.cart.models.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponWebClient {

    private final WebClient.Builder couponWebClient;

    public Optional<Coupon> getCouponByCode(String code){
        return this.couponWebClient.build().get()
                .uri("http://msvc-coupon/find-by-code/{code}", code)
                .retrieve()
                .bodyToMono(Coupon.class)
                .blockOptional();
    }
}

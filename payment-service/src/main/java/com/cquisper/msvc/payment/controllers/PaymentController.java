package com.cquisper.msvc.payment.controllers;

import com.cquisper.msvc.payment.dto.CheckoutDto;
import com.cquisper.msvc.payment.dto.PaymentDto;
import com.cquisper.msvc.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout")
    public Mono<Map<String, Object>> checkout(@RequestBody CheckoutDto checkoutDto) {
        return this.paymentService.checkout(checkoutDto)
                .map(order -> Map.of("success", true, "order", order));
    }

    @PostMapping("/payment-verification")
    public Mono<PaymentDto> paymentVerification(@RequestBody PaymentDto paymentDto) {
        return this.paymentService.paymentVerification(paymentDto);
    }
}

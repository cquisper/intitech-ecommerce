package com.cquisper.msvc.payment.service;

import com.cquisper.msvc.payment.dto.CheckoutDto;
import com.cquisper.msvc.payment.dto.PaymentDto;
import com.cquisper.msvc.payment.dto.RazorpayOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final RazorpayClient razorpayClient;

    public Mono<RazorpayOrder> checkout(CheckoutDto checkoutDto) {
        return Mono.just(new JSONObject())
                .doOnNext(orderRequest -> {
                    orderRequest.put("amount", checkoutDto.amount().multiply(BigDecimal.valueOf(100)).longValue()).put("currency", "INR");
                })
                .handle((orderRequest, sink) -> {
                    try {
                        Order order = this.razorpayClient.Orders.create(orderRequest);
                        log.info("order {}", order);
                        ObjectMapper objectMapper = new ObjectMapper();

                        RazorpayOrder razorpayOrder = objectMapper.readValue(order.toString(), RazorpayOrder.class);

                        sink.next(razorpayOrder);
                    } catch (RazorpayException | JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
    }

    public Mono<PaymentDto> paymentVerification(PaymentDto paymentDto) {
        return Mono.just(paymentDto);
    }
}

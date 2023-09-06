package com.cquisper.msvc.msvc.order.controllers;

import com.cquisper.msvc.msvc.order.dto.OrderRequest;
import com.cquisper.msvc.msvc.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> placeOrder(@RequestBody OrderRequest orderRequest) {
        return this.orderService.placeOrder(orderRequest);
    }
}
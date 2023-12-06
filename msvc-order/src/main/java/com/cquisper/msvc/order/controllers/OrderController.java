package com.cquisper.msvc.order.controllers;

import com.cquisper.msvc.order.dto.OrderRequest;
import com.cquisper.msvc.order.dto.OrderResponse;
import com.cquisper.msvc.order.models.entity.Order;
import com.cquisper.msvc.order.services.OrderService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cash-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest orderRequest,
                                  @RequestHeader("X-Email") String email) {
        Map<String, Object> response = Map.of(
                "order", this.orderService.cashOrder(orderRequest, email),
                "success", true
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderResponse>> getOrdersForUser(@RequestHeader("X-Email") String email) {
        return ResponseEntity.ok(this.orderService.getOrdersForUser(email));
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(this.orderService.updateOrderStatus(id, body.get("status")));
    }

}
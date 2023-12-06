package com.cquisper.msvc.order.services;

import com.cquisper.msvc.order.client.ProductWebClient;
import com.cquisper.msvc.order.dto.OrderItemResponse;
import com.cquisper.msvc.order.dto.OrderRequest;
import com.cquisper.msvc.order.dto.OrderResponse;
import com.cquisper.msvc.order.enums.OrderStatus;
import com.cquisper.msvc.order.models.entity.Order;
import com.cquisper.msvc.order.models.entity.OrderItem;
import com.cquisper.msvc.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductWebClient productWebClient;

    @Transactional
    public OrderResponse cashOrder(OrderRequest orderRequest, String email){
        return this.entityToDto(this.orderRepository.save(this.dtoToEntity(orderRequest, email)));
    }

    public List<OrderResponse> getOrdersForUser(String email){
        return this.orderRepository.findAllByUserOrderByCreateAtDesc(email)
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    public List<OrderResponse> getAllOrders(){
        return this.orderRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    public Order updateOrderStatus(Long id, String status){
        return this.orderRepository.findById(id)
                .map(order -> {
                    order.setOrderStatus(status);
                    return this.orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order dtoToEntity(OrderRequest orderRequest, String email){
        return Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID())
                .user(email)
                .shippingInfo(orderRequest.shippingInfo())
                .paymentInfo(orderRequest.paymentInfo())
                .orderItems(orderRequest.orderItems().stream().map(orderItemRequest -> OrderItem.builder()
                        .product(orderItemRequest.product())
                        .color(orderItemRequest.color())
                        .quantity(orderItemRequest.quantity())
                        .price(orderItemRequest.price())
                        .build()).toList()
                )
                .paidAt(LocalDateTime.now())
                .totalPrice(orderRequest.totalPrice())
                .totalPriceAfterDiscount(orderRequest.totalPriceAfterDiscount())
                .orderStatus(OrderStatus.ORDERED.getStatus())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public OrderResponse entityToDto(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .user(order.getUser())
                .shippingInfo(order.getShippingInfo())
                .paymentInfo(order.getPaymentInfo())
                .orderItems(order.getOrderItems().stream().map(orderItem -> OrderItemResponse.builder()
                        .id(orderItem.getId())
                        .product(this.productWebClient.findProductById(orderItem.getProduct()))
                        .color(orderItem.getColor())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build()).toList()
                )
                .paidAt(order.getPaidAt())
                .totalPrice(order.getTotalPrice())
                .totalPriceAfterDiscount(order.getTotalPriceAfterDiscount())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
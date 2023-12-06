package com.cquisper.msvc.order.dto;

import com.cquisper.msvc.order.models.entity.PaymentInfo;
import com.cquisper.msvc.order.models.entity.ShippingInfo;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(
        Long id,
        String orderNumber,
        String user,
        ShippingInfo shippingInfo,
        PaymentInfo paymentInfo,
        List<OrderItemResponse> orderItems,
        LocalDateTime paidAt,
        BigDecimal totalPrice,
        BigDecimal totalPriceAfterDiscount,
        String orderStatus
) {
}

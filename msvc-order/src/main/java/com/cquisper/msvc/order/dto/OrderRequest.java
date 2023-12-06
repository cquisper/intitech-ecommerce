package com.cquisper.msvc.order.dto;

import com.cquisper.msvc.order.models.entity.PaymentInfo;
import com.cquisper.msvc.order.models.entity.ShippingInfo;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderRequest(
        ShippingInfo shippingInfo,
        PaymentInfo paymentInfo,
        List<OrderItemRequest> orderItems,
        BigDecimal totalPrice,
        BigDecimal totalPriceAfterDiscount
) {
}

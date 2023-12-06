package com.cquisper.msvc.payment.dto;

import lombok.Builder;

@Builder
public record PaymentDto(
        String razorpayOrderId,
        String razorpayPaymentId
) {
}

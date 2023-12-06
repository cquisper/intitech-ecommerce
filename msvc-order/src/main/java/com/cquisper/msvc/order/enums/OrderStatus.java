package com.cquisper.msvc.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NOT_PROCESSED("Not Processed"),
    CASH_ON_DELIVERY("Cash on Delivery"),
    PROCESSING("Processing"),
    DISPATCHED("Dispatched"),
    CANCELLED("Cancelled"),
    ORDERED("Ordered"),
    DELIVERED("Delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}

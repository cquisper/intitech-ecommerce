package com.cquisper.msvc.order.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD("usd"),
    PEN("pen");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }
}

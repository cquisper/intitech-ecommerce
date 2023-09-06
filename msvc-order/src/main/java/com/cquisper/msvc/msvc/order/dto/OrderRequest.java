package com.cquisper.msvc.msvc.order.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(List<OrderLineItemsDto> orderLineItems) {
}

package com.cquisper.msvc.msvc.order.services;

import com.cquisper.msvc.msvc.order.client.InventoryWebClient;
import com.cquisper.msvc.msvc.order.dto.InventoryResponse;
import com.cquisper.msvc.msvc.order.dto.OrderRequest;
import com.cquisper.msvc.msvc.order.models.Order;
import com.cquisper.msvc.msvc.order.models.OrderLineItems;
import com.cquisper.msvc.msvc.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryWebClient inventoryClient;

    @Transactional
    public Map<String, Object> placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(orderRequest.orderLineItems()
                .stream()
                .map(orderLineItemsDto -> new OrderLineItems(orderLineItemsDto.id(),
                        orderLineItemsDto.productCode(),
                        orderLineItemsDto.price(),
                        orderLineItemsDto.quantity()))
                .toList());

        List<String> productsCode = order.getOrderLineItems().stream().map(OrderLineItems::getProductCode).toList();

        List<InventoryResponse> iventoryResponseList = this.inventoryClient.isInStock(productsCode)
                .collectList()
                .block();

        boolean allProductsInStock = Objects.requireNonNull(iventoryResponseList).stream().allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            this.orderRepository.save(order); // saved order with order line items
        }else{
            throw new IllegalArgumentException("Product out of stock");
        }

        return Map.of("info", "Order placed successfully");
    }
}

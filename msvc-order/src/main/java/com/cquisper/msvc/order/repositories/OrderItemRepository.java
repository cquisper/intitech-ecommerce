package com.cquisper.msvc.order.repositories;

import com.cquisper.msvc.order.models.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

package com.cquisper.msvc.order.repositories;

import com.cquisper.msvc.order.models.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreateAtDesc(String email);
}

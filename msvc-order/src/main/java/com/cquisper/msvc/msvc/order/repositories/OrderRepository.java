package com.cquisper.msvc.msvc.order.repositories;

import com.cquisper.msvc.msvc.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

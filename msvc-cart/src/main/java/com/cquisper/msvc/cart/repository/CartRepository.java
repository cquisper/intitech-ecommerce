package com.cquisper.msvc.cart.repository;

import com.cquisper.msvc.cart.models.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByOrderBy(Long orderBy);
}

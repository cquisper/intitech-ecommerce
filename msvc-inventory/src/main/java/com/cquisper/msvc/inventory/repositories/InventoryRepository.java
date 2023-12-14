package com.cquisper.msvc.inventory.repositories;

import com.cquisper.msvc.inventory.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProductIdIn(List<String> productIds);

    Optional<Inventory> findByProductId(String productId);

}

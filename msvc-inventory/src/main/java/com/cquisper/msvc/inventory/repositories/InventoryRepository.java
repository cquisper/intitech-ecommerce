package com.cquisper.msvc.inventory.repositories;

import com.cquisper.msvc.inventory.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProductCodeIn(List<String> productCode);

}

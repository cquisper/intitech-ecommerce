package com.cquisper.msvc.inventory.services;

import com.cquisper.msvc.inventory.dto.InventoryResponse;
import com.cquisper.msvc.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> productCode){
        return this.inventoryRepository.findByProductCodeIn(productCode)
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .productCode(inventory.getProductCode())
                        .availableQuantity(inventory.getStock())
                        .isInStock(inventory.getStock() > 0)
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory(){
        return this.inventoryRepository.findAll().stream().map(inventory ->
                InventoryResponse.builder()
                        .productCode(inventory.getProductCode())
                        .availableQuantity(inventory.getStock())
                        .isInStock(inventory.getStock() > 0)
                        .build())
                .toList();
    }
}

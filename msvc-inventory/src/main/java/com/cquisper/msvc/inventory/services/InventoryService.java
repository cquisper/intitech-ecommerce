package com.cquisper.msvc.inventory.services;

import com.cquisper.msvc.inventory.dto.InventoryRequest;
import com.cquisper.msvc.inventory.dto.InventoryResponse;
import com.cquisper.msvc.inventory.models.Inventory;
import com.cquisper.msvc.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest){
        return Optional.of(this.inventoryRepository.save(dtoToEntity(inventoryRequest)))
                .map(InventoryService::entityToDto)
                .orElseThrow(() -> new RuntimeException("Error creating inventory"));
    }

    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(String productId){
        return Optional.ofNullable(this.inventoryRepository.findByProductId(productId))
                .map(InventoryService::entityToDto)
                .orElseGet(() -> InventoryResponse.builder().sold(0).quantity(0).build());
    }

    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(Long id){
        return this.inventoryRepository.findById(id)
                .map(InventoryService::entityToDto)
                .orElseThrow(() -> new RuntimeException("Error getting inventory"));
    }

    @Transactional
    public List<InventoryResponse> updateInventories(List<Inventory> inventories){
        return this.inventoryRepository.saveAll(inventories)
                .stream()
                .map(InventoryService::entityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStockInProductIds(List<String> productIds){
        return this.inventoryRepository.findByProductIdIn(productIds)
                .stream()
                .map(InventoryService::entityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory(){
        return this.inventoryRepository.findAll()
                .stream()
                .map(InventoryService::entityToDto)
                .toList();
    }

    private static InventoryResponse entityToDto(Inventory inventory){
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .quantity(inventory.getQuantity())
                .isInStock(inventory.getQuantity() > 0)
                .sold(inventory.getSold())
                .build();
    }

    private static Inventory dtoToEntity(InventoryRequest inventoryRequest){
        return Inventory.builder()
                .productId(inventoryRequest.productId())
                .quantity(inventoryRequest.quantity())
                .sold(0) // When create a new product and is registered in inventory, the sold is 0
                .build();
    }
}

package com.cquisper.msvc.inventory.controllers;

import com.cquisper.msvc.inventory.dto.InventoryRequest;
import com.cquisper.msvc.inventory.dto.InventoryResponse;
import com.cquisper.msvc.inventory.models.Inventory;
import com.cquisper.msvc.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.inventoryService.createInventory(inventoryRequest));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(this.inventoryService.getInventoryById(id));
    }

    @GetMapping("/find-by-product-id/{productId}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable String productId){
        return ResponseEntity.ok()
                .body(this.inventoryService.getInventoryByProductId(productId));
    }

    @PutMapping("/update-all")
    public ResponseEntity<List<InventoryResponse>> updateInventories(@RequestBody List<Inventory> inventories){
        return ResponseEntity.ok()
                .body(this.inventoryService.updateInventories(inventories));
    }

    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStockInSkus(@RequestParam("product-id") List<String> productIds) {
        return this.inventoryService.isInStockInProductIds(productIds);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory(){
        return this.inventoryService.getAllInventory();
    }
}

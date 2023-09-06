package com.cquisper.msvc.inventory.controllers;

import com.cquisper.msvc.inventory.dto.InventoryResponse;
import com.cquisper.msvc.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("product-code") List<String> productCode) {
        return this.inventoryService.isInStock(productCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory(){
        return this.inventoryService.getAllInventory();
    }
}

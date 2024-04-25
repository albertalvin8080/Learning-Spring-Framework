package com.albert.inventoryservice.controllers;

import com.albert.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController
{
    private final InventoryService service;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean hasInStock(@PathVariable("sku-code") String skuCode) {
        return service.hasInStock(skuCode);
    }
}

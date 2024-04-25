package com.albert.inventoryservice.controllers;

import com.albert.core.dto.InventoryResponseDto;
import com.albert.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> hasAllInStock(@RequestParam("skuCode") List<String> skuCode) {
        return service.hasAllInStock(skuCode);
    }
}

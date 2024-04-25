package com.albert.inventoryservice.services;

import com.albert.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService
{
    private final InventoryRepository repository;

    public boolean hasInStock(String skuCode) {
        return repository.findBySkuCode(skuCode).isPresent();
    }
}

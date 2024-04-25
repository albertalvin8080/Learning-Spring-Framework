package com.albert.inventoryservice.services;

import com.albert.core.dto.InventoryResponseDto;
import com.albert.core.mappers.InventoryMapper;
import com.albert.core.models.inventory.Inventory;
import com.albert.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService
{
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    public boolean hasInStock(String skuCode) {
        final Optional<Inventory> optionalInventory = repository.findBySkuCode(skuCode);
        return optionalInventory.isPresent() && optionalInventory.get().getQuantity() > 0;
    }

    public List<InventoryResponseDto> hasAllInStock(List<String> skuCodeList) {
        final List<Inventory> inventoryList =
                repository.findAllBySkuCodeIn(skuCodeList);

        return inventoryList.stream()
                .map(mapper::from)
                .toList();
    }
}

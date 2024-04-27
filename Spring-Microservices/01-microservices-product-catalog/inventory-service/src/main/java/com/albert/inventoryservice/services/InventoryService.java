package com.albert.inventoryservice.services;

import com.albert.core.dto.InventoryResponseDto;
import com.albert.core.mappers.InventoryMapper;
import com.albert.core.models.inventory.Inventory;
import com.albert.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService
{
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    @Transactional(readOnly = true)
    public boolean hasInStock(String skuCode) {
        final Optional<Inventory> optionalInventory = repository.findBySkuCode(skuCode);
        return optionalInventory.isPresent() && optionalInventory.get().getQuantity() > 0;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> hasAllInStock(List<String> skuCodeList) throws InterruptedException {
        log.info("Wait started.");
//        Thread.sleep(10000);
        log.info("Wait ended.");

        final List<Inventory> inventoryList =
                repository.findAllBySkuCodeIn(skuCodeList);

        return inventoryList.stream()
                .map(mapper::from)
                .toList();
    }
}

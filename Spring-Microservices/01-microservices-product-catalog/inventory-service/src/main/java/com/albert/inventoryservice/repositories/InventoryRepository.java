package com.albert.inventoryservice.repositories;

import com.albert.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>
{
    @Query("SELECT i FROM Inventory i WHERE i.skuCode = :skuCode")
    Optional<Inventory> findBySkuCode(String skuCode);
}

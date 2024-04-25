package com.albert.inventoryservice;

import com.albert.inventoryservice.model.Inventory;
import com.albert.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication
{

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInventoryData(InventoryRepository repository) {
        return args -> {
            final Inventory inventory = Inventory
                    .builder()
                    .skuCode("Dark_Souls_2")
                    .quantity(43)
                    .build();

            final Inventory inventory2 = Inventory
                    .builder()
                    .skuCode("Dark_Souls_3")
                    .quantity(2)
                    .build();

            repository.saveAll(List.of(inventory, inventory2));
        };
    }
}

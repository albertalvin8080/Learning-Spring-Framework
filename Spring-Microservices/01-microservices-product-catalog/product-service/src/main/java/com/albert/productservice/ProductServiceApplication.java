package com.albert.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.albert.productservice", "com.albert.core"})
//@EnableMongoRepositories(basePackages = "com.albert.productservice.repositories")
public class ProductServiceApplication
{

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}

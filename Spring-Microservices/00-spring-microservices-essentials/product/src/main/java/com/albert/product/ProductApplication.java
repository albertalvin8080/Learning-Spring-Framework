package com.albert.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.albert.core.model"})
@EnableJpaRepositories(basePackages = {"com.albert.core.repositories"})
public class ProductApplication
{

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}

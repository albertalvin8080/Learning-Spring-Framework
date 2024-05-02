package com.albert.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.albert.core.model"})
@EnableJpaRepositories(basePackages = {"com.albert.core.repositories"})
public class ProductServiceApplication
{

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}

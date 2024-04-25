package com.albert.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.albert.orderservice", "com.albert.core"})
@EntityScan(basePackages = {"com.albert.core.models.order"})
public class OrderServiceApplication
{

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

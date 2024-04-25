package com.albert.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestApplication
{

    public static void main(String[] args) {
        SpringApplication.from(ProductServiceApplication::main).with(TestApplication.class).run(args);
    }

}

package com.albert.authenticationservice;

import com.albert.core.properties.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.albert.token", "com.albert.authenticationservice"})
@EnableDiscoveryClient
@EnableConfigurationProperties(value = JwtConfig.class)
@EntityScan(basePackages = {"com.albert.core.model"})
@EnableJpaRepositories(basePackages = {"com.albert.core.repositories"})
public class AuthenticationServiceApplication
{
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }
}

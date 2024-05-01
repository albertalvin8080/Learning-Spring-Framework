package com.albert.hashcorpvaulttesting.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties("test")
public class VaultConfig
{
    private String name;
    private String pwd;
    private String dbname;
    private String demo;
}

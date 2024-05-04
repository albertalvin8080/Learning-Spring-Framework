package com.albert.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration
{
    private String loginUrl = "/login/**";
    @NestedConfigurationProperty
    private Header header = new Header();
    private Integer expiration = 3600;
    private String privateKey = "iFDH17ipeRkWZ1BUSvm84Aj4bCSAg4pv";
    private String type = "signed"; // encrypted, signed

    @Getter
    @Setter
    public static class Header {
        private String name = "Authorization";
        private String prefix = "Bearer ";
    }
}

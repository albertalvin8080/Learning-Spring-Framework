package com.albert.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.albert.core.properties.JwtConfig;
import com.albert.gateway.security.filters.GatewayJwtTokenValidationFilter;
import com.albert.token.config.TokenSecurityConfig;
import com.albert.token.token.converter.TokenConverter;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig extends TokenSecurityConfig
{
    private final TokenConverter tokenConverter;

    public GatewaySecurityConfig(JwtConfig jwtConfig, TokenConverter tokenConverter) {
        super(jwtConfig);
        this.tokenConverter = tokenConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterAfter(new GatewayJwtTokenValidationFilter(jwtConfig, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        return super.securityFilterChain(httpSecurity);
    }
}
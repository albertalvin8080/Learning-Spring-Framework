package com.albert.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.albert.core.properties.JwtConfiguration;
import com.albert.gateway.security.filters.GatewayJwtTokenValidationFilter;
import com.albert.token.config.TokenSecurityConfig;
import com.albert.token.token.converter.TokenConverter;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig extends TokenSecurityConfig
{
    private final TokenConverter tokenConverter;

    public GatewaySecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterAfter(new GatewayJwtTokenValidationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c
                        .requestMatchers(
                                jwtConfiguration.getLoginUrl(),
                                "/swagger-ui/index.html",
                                "/*/swagger-ui/index.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/v3/api-docs/**", "/swagger-ui/**",
                                "/*/v3/api-docs/**", "/*/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers("/v1/admin/product").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        return super.securityFilterChain(httpSecurity);
    }
}
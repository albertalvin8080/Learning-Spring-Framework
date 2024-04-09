package org.albert.keycloakoauth2resourceserver.config;

import lombok.RequiredArgsConstructor;
import org.albert.keycloakoauth2resourceserver.security.CustomJwtAuthenticationTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomJwtAuthenticationTokenConverter customJwtAuthenticationTokenConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(serverConfigurer -> serverConfigurer
                .jwt(customizer -> customizer
                        .jwtAuthenticationConverter(customJwtAuthenticationTokenConverter))
        );

        http.authorizeHttpRequests(a -> a.anyRequest().authenticated());

        http.sessionManagement(c -> c
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

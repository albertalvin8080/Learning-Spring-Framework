package org.albert.cors.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${allowedOrigin.1}")
    private String allowedOrigin1;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(a -> a.anyRequest().permitAll());

        httpSecurity.cors(corsConfigurer -> corsConfigurer.configurationSource(
                request -> {
                    final CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.addAllowedOrigin(allowedOrigin1);
                    configuration.setAllowedOrigins(List.of(allowedOrigin1));
                    return configuration;
                })
        );

        return httpSecurity.build();
    }
}

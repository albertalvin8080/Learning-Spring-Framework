package org.albert.reactiveoauth2resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        http.oauth2ResourceServer(serverSpec -> serverSpec
                .jwt(jwtSpec -> jwtSpec.jwkSetUri(""))
        );

        http.authorizeExchange(a -> a
                .pathMatchers("/demo/data").hasAuthority("read")
                .anyExchange().authenticated()
        );

        return http.build();
    }
}

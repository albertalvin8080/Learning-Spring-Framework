package org.albert.oauth2_resource_server.config;

import org.albert.oauth2_resource_server.security.CustomJwtAuthenticationTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /*
     * http://localhost:9090/demo
     * Headers: {
     *   <KEY>            <VALUE>
     *   Authorization    Bearer {...}
     * }
     * ->> url for accepting Access Tokens. The token goes in place of '{...}'.
     *
     * */

    @Value("${jwkUri}")
    public String jwkUri;

    @Value("${authoritiesClaimKey}")
    private String authoritiesClaimKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                r -> r.jwt(c -> c
                        .jwkSetUri(jwkUri)
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter(authoritiesClaimKey))
                )
        );

        http.authorizeHttpRequests(a -> a.anyRequest().authenticated());

        return http.build();
    }
}

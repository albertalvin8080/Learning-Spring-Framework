package org.albert.oauth2resourceserveropaquetoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
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

    @Value("${introspectionUri}")
    private String introspectionUri;

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                c -> c.opaqueToken(c2 -> c2
                        .introspectionUri(introspectionUri)
                        .introspectionClientCredentials(clientId, clientSecret))
        );

        http.authorizeHttpRequests(a -> a.anyRequest().authenticated());

        return http.build();
    }
}

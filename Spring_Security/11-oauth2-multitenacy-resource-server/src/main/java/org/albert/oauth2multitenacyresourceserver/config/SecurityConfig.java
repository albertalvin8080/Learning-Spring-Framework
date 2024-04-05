package org.albert.oauth2multitenacyresourceserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.albert.oauth2multitenacyresourceserver.security.CustomJwtAuthenticationTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
     * http://localhost:2222/demo
     * Headers: {
     *   <KEY>            <VALUE>
     *   Authorization    Bearer {...}
     *   tokenType        [jwt|opaque]
     * }
     * ->> url for accepting Access Tokens. The token goes in place of '{...}'.
     *
     * */

    @Value("${authorizationServer.jwkUri.1}")
    private String jwkUriAuthorizationServer1;

    @Value("${opaqueTokenIntrospector.uri.1}")
    private String opaqueTokenIntrospectorUri1;
    @Value("${opaqueTokenIntrospector.clientId.1}")
    private String opaqueTokenIntrospectorClientId1;
    @Value("${opaqueTokenIntrospector.clientSecret.1}")
    private String opaqueTokenIntrospectorClientSecret1;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                j -> j.authenticationManagerResolver(
                        authenticationManagerResolver(
                                jwtDecoder(),
                                opaqueTokenIntrospector()
                        )
                )
        );

        http.authorizeHttpRequests(a -> a.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector
    ) {

        JwtAuthenticationProvider jwtProvider = new JwtAuthenticationProvider(jwtDecoder);
        // Necessary to add the authorities of the token to the Authentication Object.
        // It's important if you want to add authorization rules (like method authorizations)
        jwtProvider.setJwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter());

        AuthenticationManager jwtManager = new ProviderManager(jwtProvider);
        AuthenticationManager opaqueTokenManager =
                new ProviderManager(new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector));

        return context -> {
            if ("jwt".equals(context.getHeader("tokenType")))
                return jwtManager;
            else
                return opaqueTokenManager;
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
//                .withIssuerLocation()
                .withJwkSetUri(jwkUriAuthorizationServer1)
                .build();
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return new SpringOpaqueTokenIntrospector(
                opaqueTokenIntrospectorUri1,
                opaqueTokenIntrospectorClientId1,
                opaqueTokenIntrospectorClientSecret1
        );
    }

//    @Bean
//    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {
//        // The fetch for the Public Key (for validating the token's signature) is done automatically since you already provides the Issuers.
//        JwtIssuerAuthenticationManagerResolver resolver =
//                JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers(
//                        "http://localhost:8080",
//                        "http://localhost:1111"
//                );
//
//        return resolver;
//    }
}

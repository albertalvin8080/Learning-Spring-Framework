package com.albert.token.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.albert.core.properties.JwtConfiguration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenSecurityConfig {
    protected final JwtConfiguration jwtConfiguration;

    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(c -> c.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // This can lead to confusion while debugging, like a 401 response when in fact it was 503.
//                .exceptionHandling(c -> c.authenticationEntryPoint(
//                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
//                ))
                .authorizeHttpRequests(c -> c
                        .requestMatchers(jwtConfiguration.getLoginUrl()).permitAll()
                        .requestMatchers("/v1/admin/product").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }
}

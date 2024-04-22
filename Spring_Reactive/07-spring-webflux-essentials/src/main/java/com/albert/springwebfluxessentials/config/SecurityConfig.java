package com.albert.springwebfluxessentials.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(a -> a
                        .pathMatchers(HttpMethod.GET, "/product/**").hasRole("USER")
                        .pathMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/product/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Not needed. It works with the default 'ReactiveAuthenticationManager' Spring provides.
//    @Bean
//    public ReactiveAuthenticationManager reactiveAuthenticationManager(
//            AppUserDetailsService appUserDetailsService
//    ) {
//        return new UserDetailsRepositoryReactiveAuthenticationManager(appUserDetailsService);
//    }

    // Not needed since the users are now stored in database.
//    @Bean
//    public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
//        final UserDetails u1 = User.withUsername("albert")
//                .password("1234")
//                .passwordEncoder(pwd -> passwordEncoder().encode(pwd))
//                .roles("USER", "ADMIN")
//                .build();
//
//        final UserDetails u2 = User.withUsername("lucas")
//                .password("1234")
//                .passwordEncoder(pwd -> passwordEncoder().encode(pwd))
//                .roles("USER")
//                .build();
//
//        return new MapReactiveUserDetailsService(u1, u2);
//    }
}

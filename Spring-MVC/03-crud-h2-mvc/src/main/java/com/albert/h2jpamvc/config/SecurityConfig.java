//package com.albert.h2jpamvc.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrfConfigurer -> csrfConfigurer.disable())
//                .cors(c -> c.disable())
//                .authorizeHttpRequests(a -> a.anyRequest().permitAll());
//
//        return http.build();
//    }
//}

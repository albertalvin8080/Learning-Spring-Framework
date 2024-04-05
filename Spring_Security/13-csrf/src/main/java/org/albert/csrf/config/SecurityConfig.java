package org.albert.csrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(a -> a.anyRequest().permitAll()); // otherwise would need to always log in

//        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/demo/smth"));

        return httpSecurity.build();
    }
}

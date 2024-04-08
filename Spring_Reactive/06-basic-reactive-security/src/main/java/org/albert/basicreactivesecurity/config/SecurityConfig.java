package org.albert.basicreactivesecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(a -> a
                        .pathMatchers("/demo/data").hasAuthority("read")
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return  serverHttpSecurity.build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        final UserDetails u1 = User
                .withUsername("albert")
                .password("1234")
                .passwordEncoder(p -> passwordEncoder().encode(p))
                .authorities("read", "write")
                .build();

        return new MapReactiveUserDetailsService(u1);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

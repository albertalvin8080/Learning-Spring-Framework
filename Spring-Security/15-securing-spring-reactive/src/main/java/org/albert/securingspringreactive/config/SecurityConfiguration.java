package org.albert.securingspringreactive.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        CookieServerCsrfTokenRepository csrfTokenRepository =
                CookieServerCsrfTokenRepository.withHttpOnlyFalse();
//        csrfTokenRepository.setCookieCustomizer(c -> c
//                .sameSite("Lax")
//                .secure(false) // Set to true when deploying over HTTPS
//        ); // not necessary

        serverHttpSecurity
                .authorizeExchange(a -> a
                        .pathMatchers("/demo/data").hasAuthority("write")
                        .anyExchange().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfSpec -> csrfSpec
                        .csrfTokenRepository(csrfTokenRepository)
                );

//        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()); // testing purpose

        return serverHttpSecurity.build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        final UserDetails u1 = User
                .withUsername("albert")
                .password("1234")
                .passwordEncoder(p -> passwordEncoder().encode(p))
                .authorities("read", "write")
                .build();

        final UserDetails u2 = User
                .withUsername("lucas")
                .password("1234")
                .passwordEncoder(p -> passwordEncoder().encode(p))
                .authorities("read")
                .build();

        return new MapReactiveUserDetailsService(u1, u2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

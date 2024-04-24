package org.albert.firsttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    // BEAN WAS MOVED
//    @Bean
//    public UserDetailsService userDetailsService() {
//        final InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
//
//        final UserDetails userDetails =
//                User.withUsername("albert")
//                    .password("12345")
//                    .roles("ADMIN")
//                    .passwordEncoder(pwd -> passwordEncoder().encode(pwd))
//                    .build();
//
//        detailsManager.createUser(userDetails);
//
//        return detailsManager;
//    }
}

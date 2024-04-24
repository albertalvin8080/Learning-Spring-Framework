package org.albert.multipleauthenticationmanagers.config;

import org.albert.multipleauthenticationmanagers.security.filters.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    /*
    WARNING: If you add any instance of AuthenticationManager or AuthenticationProvider to the Spring Context,
    the spring security autoconfiguration will be overriden, and you will not be able to use httpBasic().
    SOLUTION: Instantiate the classes directly inside each other.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, ApiKeyFilter apiKeyFilter) throws Exception {
        return httpSecurity
                .addFilterBefore(apiKeyFilter, BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .build();
    }

//    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final UserDetails details = User
                .withUsername("alllmer")
                .password("1234")
                .build();

        return new InMemoryUserDetailsManager(details);
    }

//    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

package org.albert.endpointauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(a -> {
//                    a.anyRequest().hasAuthority("read");
//                    a.requestMatchers("/demo").hasAuthority("read");
//                    a.anyRequest().authenticated();
//                    a.anyRequest().access("isAuthenticated() and hasAuthority('read')");
//                    a.anyRequest().access(this::hasReadAuthority);
//                    a.requestMatchers(HttpMethod.GET, "/demo/demo1").hasRole("ADMIN");
                    a.requestMatchers(HttpMethod.GET, "/demo/**").hasRole("ADMIN");
                    a.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable()) // NEVER DO THIS. ONLY FOR EXAMPLES
                .build();
    }

    private AuthorizationDecision hasReadAuthority(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        Authentication authentication = authenticationSupplier.get();
        boolean hasReadAuthority =
                authentication.isAuthenticated() && authentication.getAuthorities().contains(new SimpleGrantedAuthority("read"));
        return new AuthorizationDecision(hasReadAuthority);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails u1 = User
                .withUsername("albert")
                .password("1234")
                .passwordEncoder(p -> passwordEncoder().encode(p))
                .authorities("read")
                .roles("USER", "ADMIN")
                .build();

        final UserDetails u2 = User
                .withUsername("lucas")
                .password("1234")
                .passwordEncoder(p -> passwordEncoder().encode(p))
                .authorities("write")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(u1, u2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.albert.authenticationservice.security.config;

import com.albert.authenticationservice.security.filter.JwtUsernamePasswordAuthenticationFilter;
import com.albert.authenticationservice.security.user.AppUserDetailsService;
import com.albert.core.properties.JwtConfiguration;
import com.albert.token.config.TokenSecurityConfig;
import com.albert.token.filters.JwtTokenValidationFilter;
import com.albert.token.token.converter.TokenConverter;
import com.albert.token.token.creator.TokenCreator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthServiceSecurityConfig extends TokenSecurityConfig
{
    public AuthServiceSecurityConfig(JwtConfiguration jwtConfiguration) {
        super(jwtConfiguration);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AppUserDetailsService appUserDetailsService, TokenCreator tokenCreator, TokenConverter tokenConverter) throws Exception {
        httpSecurity
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(
                                providerManager(appUserDetailsService),
                                jwtConfiguration,
                                tokenCreator
                        )
                )
                .addFilterAfter(
                        new JwtTokenValidationFilter(jwtConfiguration, tokenConverter),
                        UsernamePasswordAuthenticationFilter.class
                );
        return super.securityFilterChain(httpSecurity);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private ProviderManager providerManager(AppUserDetailsService appUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
        provider.setUserDetailsService(appUserDetailsService);
        return new ProviderManager(provider);
    }
}

package com.albert.authenticationservice.security.filter;

import com.albert.core.model.AppUser;
import com.albert.core.properties.JwtConfig;
import com.albert.token.token.creator.TokenCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final TokenCreator tokenCreator;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword());
            token.setDetails(appUser);
            return authenticationManager.authenticate(token);
        }
        catch (Exception e) {
            // Never do this in production.
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            final SignedJWT signedJWT = tokenCreator.createSignedJWT(authResult);
            final String encryptedJWS = tokenCreator.encryptJWS(signedJWT);
            response.setHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfig.getHeader().getName());
            response.addHeader(jwtConfig.getHeader().getName(), jwtConfig.getHeader().getPrefix() + encryptedJWS);
        }
        catch (Exception e) {
            // Never do this in production.
            throw new RuntimeException(e);
        }
    }
    
}
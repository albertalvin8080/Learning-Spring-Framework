package com.albert.gateway.security.filters;

import java.io.IOException;

import com.albert.gateway.security.header.MutableHttpServletRequest;
import com.albert.token.filters.JwtTokenValidationFilter;

import com.albert.core.properties.JwtConfiguration;
import com.albert.token.token.converter.TokenConverter;
import com.albert.token.token.util.TokenSecurityContextUtil;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.server.ServerHttpRequest;

public class GatewayJwtTokenValidationFilter extends JwtTokenValidationFilter
{
    public GatewayJwtTokenValidationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();
        try {
            final SignedJWT signedJWT = decryptAndValidateToken(token);

            // Yes, this line is necessary because you're doing endpoint-level authorization inside this gateway.
            TokenSecurityContextUtil.setTokenInsideSecurityContext(signedJWT);

            if (jwtConfiguration.getType().equalsIgnoreCase("signed")) {
                request = new MutableHttpServletRequest(request)
                        .putHeader(
                                jwtConfiguration.getHeader().getName(),
                                jwtConfiguration.getHeader().getPrefix() + signedJWT.serialize()
                        );
            }

            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            // Again, never do this in production.
            throw new RuntimeException(e);
        }
    }

}

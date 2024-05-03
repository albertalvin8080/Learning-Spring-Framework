package com.albert.gateway.security.filters;

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.albert.core.properties.JwtConfig;
import com.albert.token.token.converter.TokenConverter;
import com.albert.token.token.util.TokenSecurityUtil;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GatewayJwtTokenValidationFilter extends OncePerRequestFilter 
{
    private final JwtConfig jwtConfig;
    private final TokenConverter tokenConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(jwtConfig.getHeader().getName());

        if(header == null || !header.startsWith(jwtConfig.getHeader().getPrefix()))
            filterChain.doFilter(request, response);

        try {
            String encryptedToken = header.replace(jwtConfig.getHeader().getPrefix(), "").trim();
            SignedJWT signedJWT = tokenConverter.decryptToken(encryptedToken);
            tokenConverter.validateTokenSignature(signedJWT);

            TokenSecurityUtil.setTokenInsideSecurityContext(signedJWT);

            filterChain.doFilter(request, response);
        } 
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.albert.token.filters;

import com.albert.core.properties.JwtConfiguration;
import com.albert.token.token.converter.TokenConverter;
import com.albert.token.token.util.TokenSecurityContextUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;

@RequiredArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter
{
    protected final JwtConfiguration jwtConfiguration;
    protected final TokenConverter tokenConverter;

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
            SignedJWT signedJWT = jwtConfiguration.getType().equals("encrypted") ?
                    decryptAndValidateToken(token)
                    : validateToken(token);

            TokenSecurityContextUtil.setTokenInsideSecurityContext(signedJWT);

            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            // Again, never do this in production.
            throw new RuntimeException(e);
        }
    }

    protected SignedJWT decryptAndValidateToken(String encryptedToken) throws ParseException, JOSEException, AccessDeniedException {
        final SignedJWT signedJWT = tokenConverter.decryptToken(encryptedToken);
        tokenConverter.validateTokenSignature(signedJWT);
        return signedJWT;
    }

    protected SignedJWT validateToken(String signedJWT) throws AccessDeniedException, ParseException, JOSEException {
        tokenConverter.validateTokenSignature(signedJWT);
        return SignedJWT.parse(signedJWT);
    }
}

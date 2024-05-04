package com.albert.gateway.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.util.List;

@Slf4j
public class TokenSecurityContextUtil
{
    private TokenSecurityContextUtil() {}

    public static void setTokenInsideReactiveSecurityContext(SignedJWT signedJWT) {
        try {
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            String username = jwtClaimsSet.getSubject();
            if(username == null)
                throw new JOSEException("Subject missing from JWTClaimSet.");

            List<String> claimList = jwtClaimsSet.getStringListClaim("authorities");
            List<SimpleGrantedAuthority> authorities = claimList.stream().map(SimpleGrantedAuthority::new).toList();

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

            ReactiveSecurityContextHolder.withAuthentication(auth);
        }
        catch (ParseException | JOSEException e) {
            log.error("Failed to insert SignedJWT inside Security Context", e);
            SecurityContextHolder.clearContext();
        }
    }
}

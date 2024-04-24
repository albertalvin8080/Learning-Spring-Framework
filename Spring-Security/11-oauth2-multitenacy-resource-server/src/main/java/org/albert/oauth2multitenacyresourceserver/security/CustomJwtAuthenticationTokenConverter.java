package org.albert.oauth2multitenacyresourceserver.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        final List<String> authorities = (List<String>) source.getClaims().get("authorities");
        return new JwtAuthenticationToken(
                source,
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}

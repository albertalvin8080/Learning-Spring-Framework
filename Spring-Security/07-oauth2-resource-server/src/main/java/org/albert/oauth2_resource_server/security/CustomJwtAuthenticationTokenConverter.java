package org.albert.oauth2_resource_server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {

//    @Value("${authoritiesClaimKey}")
    private final String authoritiesClaimKey; // you cannot inject here because this class is not a Spring Bean

    public CustomJwtAuthenticationTokenConverter(String authoritiesClaimKey) {
        this.authoritiesClaimKey = authoritiesClaimKey;
    }

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        final List<String> authorities = (List<String>) source.getClaims().get(authoritiesClaimKey);
        return new JwtAuthenticationToken(
                source,
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}

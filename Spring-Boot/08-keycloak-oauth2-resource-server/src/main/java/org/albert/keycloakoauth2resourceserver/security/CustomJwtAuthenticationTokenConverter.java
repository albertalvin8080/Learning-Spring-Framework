package org.albert.keycloakoauth2resourceserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.principal.claim-name}")
    private String principalClaimName;
    @Value("${jwt.resource-access.claim-name}")
    private String resourceAccessClaimName;
    @Value("${jwt.resource.claim-name}")
    private String resourceClaimName;
    @Value("${jwt.roles.claim-name}")
    private String rolesClaimName;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        final Set<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                source,
                authorities,
                extractPrincipalClaimName(source)
        );
    }

    private String extractPrincipalClaimName(Jwt source) {
        String claimName = JwtClaimNames.SUB;

        if(principalClaimName != null)
            claimName = principalClaimName;

        return source.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt source) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> roles;

        resourceAccess = source.getClaim(resourceAccessClaimName);
        if (resourceAccess == null)
            return Set.of();

        resource = (Map<String, Object>) resourceAccess.get(resourceClaimName);
        if (resource == null)
            return Set.of();

        roles = (Collection<String>) resource.get(rolesClaimName);
        if (roles == null)
            return Set.of();

        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}

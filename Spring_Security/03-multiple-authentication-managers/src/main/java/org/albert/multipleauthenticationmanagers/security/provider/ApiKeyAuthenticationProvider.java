package org.albert.multipleauthenticationmanagers.security.provider;

import lombok.RequiredArgsConstructor;
import org.albert.multipleauthenticationmanagers.security.authentication.ApiKeyAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiKeyAuthentication a = (ApiKeyAuthentication) authentication;

        if(a.key().equals(key)) {
            return new ApiKeyAuthentication(true, null);
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }
}

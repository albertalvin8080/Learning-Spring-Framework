package org.albert.multipleauthenticationmanagers.security.managers;

import lombok.RequiredArgsConstructor;
import org.albert.multipleauthenticationmanagers.security.provider.ApiKeyAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class ApiKeyAuthenticationManager implements AuthenticationManager {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final ApiKeyAuthenticationProvider provider = new ApiKeyAuthenticationProvider(key);

        if(provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }

        throw new BadCredentialsException("No matching provider was found.");
    }
}

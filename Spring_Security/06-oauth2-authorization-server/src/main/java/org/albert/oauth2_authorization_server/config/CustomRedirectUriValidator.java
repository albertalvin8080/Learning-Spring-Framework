package org.albert.oauth2_authorization_server.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.function.Consumer;

public class CustomRedirectUriValidator implements Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> {
    @Override
    public void accept(OAuth2AuthorizationCodeRequestAuthenticationContext context) {
        final OAuth2AuthorizationCodeRequestAuthenticationToken authentication = context.getAuthentication();
        final String uri = authentication.getRedirectUri();
        final RegisteredClient registeredClient = context.getRegisteredClient();

        if(!registeredClient.getRedirectUris().contains(uri)) {
            final OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REDIRECT_URI);
            throw new OAuth2AuthorizationCodeRequestAuthenticationException(error, authentication);
        }
    }
}

package org.albert.oauth2_authorization_server.mappers;

import org.albert.oauth2_authorization_server.entities.registered_client.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public interface RegisteredClientMapper {
    static AppRegisteredClient from(RegisteredClient registeredClient) {
        final AppRegisteredClient app = new AppRegisteredClient();

        app.setId(registeredClient.getId());
        app.setClientId(registeredClient.getClientId());
        app.setClientSecret(registeredClient.getClientSecret());

        app.setScopeSet(registeredClient
                .getScopes()
                .stream()
                .map(scope -> new AppScope(null, scope, Set.of(app)))
                .collect(Collectors.toSet())
        );

        app.setRedirectUriSet(registeredClient
                .getRedirectUris()
                .stream()
                .map(uri -> new AppRedirectUri(null, uri, app))
                .collect(Collectors.toSet())
        );

        app.setClientAuthenticationMethodSet(registeredClient
                .getClientAuthenticationMethods()
                .stream()
                .map(method -> new AppClientAuthenticationMethod(null, method.getValue(), Set.of(app)))
                .collect(Collectors.toSet())
        );

        app.setAuthorizationGrantTypeSet(registeredClient
                .getAuthorizationGrantTypes()
                .stream()
                .map(auth -> new AppAuthorizationGrantType(null, auth.getValue(), Set.of(app)))
                .collect(Collectors.toSet())
        );

        return app;
    }

    static RegisteredClient from(AppRegisteredClient app) {
        System.out.println(
                "RegisteredClientMapper says: " +
                app.getRedirectUriSet().stream().map(AppRedirectUri::getUri).collect(Collectors.toSet())
        );
        return RegisteredClient
                .withId(app.getId())
                .clientId(app.getClientId())
                .clientSecret(app.getClientSecret())
                .redirectUris(set ->
                        set.addAll(app.getRedirectUriSet()
                                .stream().map(AppRedirectUri::getUri).collect(Collectors.toSet())
                        )
                )
                .scopes(set ->
                        set.addAll(app.getScopeSet()
                                .stream().map(AppScope::getName).collect(Collectors.toSet())
                        )
                )
                .clientAuthenticationMethods(set ->
                        set.addAll(app.getClientAuthenticationMethodSet()
                                .stream()
                                .map(CAMA -> new ClientAuthenticationMethod(CAMA.getName()))
                                .collect(Collectors.toSet())
                        )
                )
                .authorizationGrantTypes(set ->
                        set.addAll(app.getAuthorizationGrantTypeSet()
                                .stream()
                                .map(AGTA -> new AuthorizationGrantType(AGTA.getName()))
                                .collect(Collectors.toSet())
                        )
                )
                .tokenSettings(
                        TokenSettings
                                .builder()
                                 //testing Opaque Tokens
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofDays(1)) // debug purpose
                                .build()
                )
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // used in the client app
                .build();
    }
}

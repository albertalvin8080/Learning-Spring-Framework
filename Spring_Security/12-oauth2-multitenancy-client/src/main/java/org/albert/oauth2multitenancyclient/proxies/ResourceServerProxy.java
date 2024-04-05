package org.albert.oauth2multitenancyclient.proxies;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceServerProxy {

    private final ResourceServerFeignClient feignClient;
    private final OAuth2AuthorizedClientManager manager;

    public String getDataUsingAuthorizationServer1() {
        final OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client")
                .build();

        final OAuth2AuthorizedClient authorized = manager.authorize(request);
        final String tokenValue = authorized.getAccessToken().getTokenValue();

        final String data = feignClient.getData("Bearer " + tokenValue, "jwt");

        return data;
    }

    public String getDataUsingAuthorizationServer2() {
        final OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("2")
                .principal("reactClient")
                .build();

        final OAuth2AuthorizedClient authorized = manager.authorize(request);
        final String tokenValue = authorized.getAccessToken().getTokenValue();

        final String data = feignClient.getData("Bearer " + tokenValue, "opaque");

        return data;
    }
}

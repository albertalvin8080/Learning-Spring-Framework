package org.albert.oauth2client.proxies;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceServerProxy {

    // Smart Object. It doesn't make a new request if the previously requested token is still valid.
    private final OAuth2AuthorizedClientManager manager;
    // If you see a red line, add @EnableFeignClients to your app class.
    private final ResourceServerFeignClient resourceServerFeignClient;

    public String getData() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client")
                .build();

        /*
         * WARNING: don't forget to add '.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)'
         * to your Authorization Server.
         * */
        // the name of the method is 'authorize()'. It means it's making a request to the Authorization Server.
        final OAuth2AuthorizedClient client = manager.authorize(request);
        String token = client.getAccessToken().getTokenValue();

        System.out.println("TOKEN: " + token);

        // requesting the resource from the Resource Server
        final String data = resourceServerFeignClient.getData("Bearer " + token);

        return data;
    }
}

package org.albert.oauth2_authorization_server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class SecurityConfig {

    /*
     * WARNING:
     *   - You must send a POST request with Http Basic -> username: client, password: secret (see init.sql for more details).
     *   - You must use 'x-www-form-urlencoded' instead of query parameters in newer versions of Spring Security (2024.04).
     *   - You must remove the padding (char '=') from the PKCE verifier and challenge.
     * */

    /*
    *  verifier:  OE_6w9dfqUKQe1lz6JKn2RYnKV2GRT5mGwzZzfrBr2s
    *  challenge: fxUGtG8aCl3bUADZ3eQJEp6R-0ilafRflhdE3X29_1U
    *
    *
    * - client_id: 'client'
    * - redirect_uri: 'https://springone.io/authorized'
    * ->>
    * http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://springone.io/authorized&code_challenge=fxUGtG8aCl3bUADZ3eQJEp6R-0ilafRflhdE3X29_1U&code_challenge_method=S256
    * ->>
    * Get the authorization code
    *
    *
    * - client_id: 'reactClient' (it's just a random name. See init.sql for more info.)
    * - redirect_uri: 'http://localhost:8080/oauth2/jwks'
    * ->>
    * http://localhost:8080/oauth2/authorize?response_type=code&client_id=reactClient&scope=openid&redirect_uri=http://localhost:8080/oauth2/jwks&code_challenge=fxUGtG8aCl3bUADZ3eQJEp6R-0ilafRflhdE3X29_1U&code_challenge_method=S256
    * ->>
    * Get the authorization code
    *
    *
    * http://localhost:8080/oauth2/token
    * body (x-www-form-urlencoded): {
    *     <KEY>            <VALUE>
    *     client_id     =  client
    *     redirect_uri  =  https://springone.io/authorized
    *     grant_type    =  authorization_code
    *     code_verifier =  OE_6w9dfqUKQe1lz6JKn2RYnKV2GRT5mGwzZzfrBr2s
    *     code          =  {...}
    * }
    * ->> Get the access token. The last parameter 'code = ' receives the authorization code (get it using the previous link).
    *
    *
    * http://localhost:8080/oauth2/jwks
    * ->> See the public key, the kid (key id), algorithm, etc.
    *
    *
    * http://localhost:8080/.well-known/openid-configuration
    * ->> See openid configurations (like the Introspection Endpoint for opaque tokens).
    *
    *
    * http://localhost:8080/oauth2/introspect
    * body (x-www-form-urlencoded): {
    *     <KEY>     <VALUE>
    *     token  =  {...}
    * }
    * ->> Send the Opaque Token to get information about the Resource Owner and the Client.
    *
    *
    * https://jwt.io/
    * ->> See the content of a JWT Token.
    *
    * */

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .authorizationEndpoint(
                        configurer -> configurer.authenticationProviders(getAuthenticationEndpointProviders())
                )
                .oidc(Customizer.withDefaults());

        http.exceptionHandling(
                e -> e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                )
        );

        return http.build();
    }

    private Consumer<List<AuthenticationProvider>> getAuthenticationEndpointProviders() {
        return providers -> {
            for(var p : providers) {
                if(p instanceof OAuth2AuthorizationCodeRequestAuthenticationProvider x)
                    x.setAuthenticationValidator(new CustomRedirectUriValidator());
            }
        };
    }

    @Bean
    // creates a second instance of SecurityFilterChain [@Order(2)] which will be applied to the request only if it passes through the previous SecurityFilterChain [@Order(1)].
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(a -> a.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        final KeyPair keyPair = generator.generateKeyPair();

        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID(UUID.randomUUID().toString())
                .privateKey(privateKey)
                .build();

        final JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // Used to alter the structure of the token, like adding 'key' : 'value' inside the JWT Token.
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            context.getClaims().claim("test-name", "test-value");
            final List<String> authorities = context.getPrincipal().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            context.getClaims().claim("authorities", authorities);
        };
    }

/*
* The beans below (RegisteredClientRepository and UserDetailsService)
* have been replaced with other ones with access to a database.
* */

//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        final RegisteredClient registeredClient = RegisteredClient
//                .withId(UUID.randomUUID().toString())
//                .clientId("client")
//                .clientSecret("secret")
//                .scope(OidcScopes.OPENID) // this says the client's intention is to authenticate a user
//                .scope(OidcScopes.PROFILE) // see OneNote for more details
//                .redirectUri("https://springone.io/authorized")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // http basic
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                /*
//                 * http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://localhost:8080/oauth2/jwks&code_challenge=fxUGtG8aCl3bUADZ3eQJEp6R-0ilafRflhdE3X29_1U&code_challenge_method=S256
//                 * ->> testing the custom redirect_uri
//                 *
//                 * WARNING: Apparently, the redirect_uri now works for //localhost URLs by default.
//                 */
//                .redirectUri("http://localhost:8080/oauth2/jwks")
//                .tokenSettings(
//                        TokenSettings
//                                .builder()
//                                // OAuth2TokenFormat.REFERENCE      -> Opaque Token
//                                // OAuth2TokenFormat.SELF_CONTAINED -> Non-opaque Token (JWT)
////                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
//                                .accessTokenTimeToLive(Duration.ofSeconds(400))
//                                .build()
//                )
//                .build();
//
//        return new InMemoryRegisteredClientRepository(registeredClient);
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        final UserDetails u1 = User.withUsername("fern")
//                .password("1234")
//                .authorities("read")
//                .build();
//
//        final UserDetails u2 = User.withUsername("frieren")
//                .password("1234")
//                .authorities("read", "write")
//                .build();
//
//        return new InMemoryUserDetailsManager(u1, u2);
//    }
}

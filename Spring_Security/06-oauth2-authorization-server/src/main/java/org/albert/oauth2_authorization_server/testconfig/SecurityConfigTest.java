//package org.albert.oauth2_authorization_server.testconfig;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
//import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
//import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
//import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
//import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.time.Duration;
//import java.util.List;
//import java.util.UUID;
//import java.util.function.Consumer;
//
//@Configuration
//public class SecurityConfigTest {
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain authenticationSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
//                .authorizationEndpoint(
//                        configurer -> configurer.authenticationProviders(getAuthenticationEndpointProviders())
//                )
//                .oidc(Customizer.withDefaults());
//
//        http.exceptionHandling(
//                e -> e.authenticationEntryPoint(
//                        new LoginUrlAuthenticationEntryPoint("/login")
//                )
//        );
//
//        return http.build();
//    }
//
//    private Consumer<List<AuthenticationProvider>> getAuthenticationEndpointProviders() {
//        return providers -> {
//            for (var provider : providers)
//                if(provider instanceof OAuth2AuthorizationCodeRequestAuthenticationProvider x)
//                    x.setAuthenticationValidator(new CustomRedirectUriValidatorTest());
//        };
//    }
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.formLogin(Customizer.withDefaults())
//                .authorizeHttpRequests(a -> a.anyRequest().authenticated());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        final UserDetails u1 = User.withUsername("fern").password("1234").authorities("read").build();
//        return new InMemoryUserDetailsManager(u1);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        final RegisteredClient registeredClient = RegisteredClient
//                .withId(UUID.randomUUID().toString())
//                .clientId("client")
//                .clientSecret("secret")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .redirectUri("https://springone.io/authorized")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .tokenSettings(
//                        TokenSettings
//                                .builder()
//                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
//                                .accessTokenTimeToLive(Duration.ofSeconds(400))
//                                .build()
//                )
//                .build();
//
//        return new InMemoryRegisteredClientRepository(registeredClient);
//    }
//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings
//                .builder()
//                .build();
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
//        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//        generator.initialize(2048);
//        final KeyPair keyPair = generator.generateKeyPair();
//
//        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        final RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .keyID(UUID.randomUUID().toString())
//                .privateKey(privateKey)
//                .build();
//
//        final JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }
//
//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
//        return context -> {
//            context.getClaims().claim("testkey", "testvalue");
//        };
//    }
//
//}

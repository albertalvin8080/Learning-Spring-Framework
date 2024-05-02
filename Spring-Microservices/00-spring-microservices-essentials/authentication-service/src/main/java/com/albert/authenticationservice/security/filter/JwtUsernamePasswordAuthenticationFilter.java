package com.albert.authenticationservice.security.filter;

import com.albert.core.model.AppUser;
import com.albert.core.properties.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword(), Collections.emptyList());
            token.setDetails(appUser);
            return authenticationManager.authenticate(token);
        }
        catch (Exception e) {
            // Don't do this in production.
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            final SignedJWT signedJWT = createSignedJWT(authResult);
            final String encryptedToken = encryptToken(signedJWT);
            response.setHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, "+ jwtConfig.getHeader().getName());
            response.addHeader(jwtConfig.getHeader().getName(), jwtConfig.getHeader().getPrefix() + encryptedToken);
        }
        catch (Exception e) {
            // Don't do this in production.
            throw new RuntimeException(e);
        }
    }

    private SignedJWT createSignedJWT(Authentication authentication) throws NoSuchAlgorithmException, JOSEException {
        final AppUser appUser = (AppUser) authentication.getDetails();
        final JWTClaimsSet jwtClaimSet = createJWTClaimSet(authentication, appUser);

        final KeyPair keyPair = generateKeyPair();
        final JWK publicRSAKey = new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .keyID(UUID.randomUUID().toString())
                .build();

        final SignedJWT signedJWT = new SignedJWT(
                new JWSHeader
                        .Builder(JWSAlgorithm.RS256)
                        .jwk(publicRSAKey)
                        .type(JOSEObjectType.JWT)
                        .build(),
                jwtClaimSet
        );

        RSASSASigner signer = new RSASSASigner(keyPair.getPrivate());

        signedJWT.sign(signer);
        log.info("Signed JWT: {}", signedJWT.serialize());
        return signedJWT;
    }

    private JWTClaimsSet createJWTClaimSet(Authentication authentication, AppUser appUser) {
        return new JWTClaimsSet.Builder()
                .subject(appUser.getUsername())
                .claim("authorities",
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                .issuer("http://albert.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfig.getExpiration() * 1000)))
                .build();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    private String encryptToken(SignedJWT signedJWT) throws JOSEException {
        final DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfig.getPrivateKey().getBytes());

        final JWEObject jweObject = new JWEObject(
                new JWEHeader
                        .Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                        .contentType("JWT")
                        .build(),
                new Payload(signedJWT)
        );

        jweObject.encrypt(directEncrypter);
        return jweObject.serialize();
    }

}

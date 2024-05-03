package com.albert.token.token.creator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.albert.core.model.AppUser;
import com.albert.core.properties.JwtConfig;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenCreator {
    private final JwtConfig jwtConfig;

    public SignedJWT createSignedJWT(Authentication authentication) throws NoSuchAlgorithmException, JOSEException {
        final AppUser appUser = (AppUser) authentication.getDetails();

        final JWTClaimsSet jwtClaimSet = createJWTClaimSet(authentication, appUser);
        final KeyPair keyPair = generateKeyPair();

        final JWK rsaPublicKey = new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .keyID(UUID.randomUUID().toString())
                .build();

        final SignedJWT signedJWT = new SignedJWT(
                new JWSHeader
                        .Builder(JWSAlgorithm.RS256)
                        .jwk(rsaPublicKey)
                        .type(JOSEObjectType.JWT)
                        .build(),
                jwtClaimSet
        );

        final RSASSASigner signer = new RSASSASigner(keyPair.getPrivate());
        signedJWT.sign(signer);
        log.info("Signed JWT: {}", signedJWT.serialize());

        return signedJWT;
    }

    private JWTClaimsSet createJWTClaimSet(Authentication authentication, AppUser appUser) {
        return new JWTClaimsSet.Builder()
                .subject(appUser.getUsername())
                .claim("authorities", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .issuer("http://albert.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfig.getExpiration()) * 1000))
                .build();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public String encryptJWS(SignedJWT signedJWT) throws JOSEException {
        final DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfig.getPrivateKey().getBytes());

        final JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                        .contentType("JWT")
                        .build(),
                new Payload(signedJWT)
        );

        jweObject.encrypt(directEncrypter);
        return jweObject.serialize();
    }
}

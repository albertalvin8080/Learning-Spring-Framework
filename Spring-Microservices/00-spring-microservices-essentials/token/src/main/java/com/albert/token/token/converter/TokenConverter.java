package com.albert.token.token.converter;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.albert.core.properties.JwtConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenConverter 
{
    private final JwtConfig jwtConfig;

    public SignedJWT decryptToken(String encryptedToken) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(encryptedToken);
        DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfig.getPrivateKey().getBytes());
        jweObject.decrypt(directDecrypter);
        return jweObject.getPayload().toSignedJWT();
    }

    public void validateTokenSignature(SignedJWT signedJWT) throws ParseException, JOSEException, AccessDeniedException {
        RSAKey rsaKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        if(!signedJWT.verify(new RSASSAVerifier(rsaKey)))
            throw new AccessDeniedException("The token signature is invalid!");
    }

    public void validateTokenSignature(String signedJWT) throws AccessDeniedException, ParseException, JOSEException {
        this.validateTokenSignature(SignedJWT.parse(signedJWT));
    }
}

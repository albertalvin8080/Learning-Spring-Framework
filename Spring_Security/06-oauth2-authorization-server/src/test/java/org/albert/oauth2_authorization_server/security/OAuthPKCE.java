package org.albert.oauth2_authorization_server.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

// Proof Key for Code Exchange (PKCE)
public class OAuthPKCE {
    public static String generateCodeVerifier() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        final String verifier = generateCodeVerifier();
        final String challenge = generateCodeChallenge(verifier);
        System.out.println("Verifier:  " + verifier);
        System.out.println("Challenge: " + challenge);
    }
}

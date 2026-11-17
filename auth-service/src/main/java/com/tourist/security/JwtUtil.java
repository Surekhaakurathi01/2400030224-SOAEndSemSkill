package com.tourist.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public final class JwtUtil {
    // Same demo key is used by Auth Service and Tourist Service.
    // For real deployment, inject a strong secret from an environment variable/secret manager.
    private static final String SECRET =
            "TouristMicroservicesDemoSecretKey2026-ReplaceForProduction!";
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private JwtUtil() {}

    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 60L * 60L * 1000L))
                .signWith(KEY)
                .compact();
    }

    public static Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token).getPayload();
    }

    public static String validateAndGetUsername(String token) {
        return parseClaims(token).getSubject();
    }
}

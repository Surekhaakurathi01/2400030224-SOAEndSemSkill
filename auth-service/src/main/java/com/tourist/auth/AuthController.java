package com.tourist.auth;

import com.tourist.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        // Demo-only credentials. Replace with database users and password hashing for production.
        if (request == null || !"admin".equals(request.username())
                || !"password123".equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String token = JwtUtil.generateToken(request.username());
        return Map.of("token", token, "tokenType", "Bearer", "expiresIn", 3600);
    }

    @GetMapping("/validate")
    public Map<String, Object> validate(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false)
                                         String authorization) {
        String token = extractBearerToken(authorization);
        try {
            Claims claims = JwtUtil.parseClaims(token);
            return Map.of("valid", true,
                    "subject", claims.getSubject(),
                    "issuedAt", claims.getIssuedAt(),
                    "expiresAt", claims.getExpiration());
        } catch (JwtException | IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
    }

    private String extractBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")
                || authorization.length() <= 7) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Bearer token");
        }
        return authorization.substring(7).trim();
    }

    public record LoginRequest(String username, String password) {}
}

package org.example.elegant_ecommerce_backend_project.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // Generate a secure random secret key (HS256 requires 256-bit key)
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Expiration time for token (e.g., 15 minutes)
    private static final long EXPIRATION_TIME_MILLIS = 15 * 60 * 1000;

    // Generate JWT token using email as subject
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validate and parse JWT token to get the email (subject)
    public static String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}

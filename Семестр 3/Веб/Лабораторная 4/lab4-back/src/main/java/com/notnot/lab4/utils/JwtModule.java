package com.notnot.lab4.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtModule {

    private static final String SECRET = "2bba2b0dce9a987b2e59f8c753ef6a455d671ba2a10cc355a6216a1ee3e4fd1b";

    private static final Key HMAC_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String createJwt(String username) {
        return Jwts.builder()
                .claim("username", username)
                .setIssuer("TestApplication")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(HMAC_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractUsername(String token) {
        return parseToken(token).getBody().get("username", String.class);
    }

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(HMAC_KEY)
                .build()
                .parseClaimsJws(token);
    }
}
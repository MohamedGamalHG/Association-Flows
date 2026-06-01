package com.mg.Association_Flows.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtHelper {
    // A secure, long, and complex secret key for signing the JWT. It should be stored securely.
    private static final String SECRET = "VxRfBGJFviiO62cg/M0YY5WypcyvtUUjfkI5aDJgwt4dLz6BQKuaKChKyn+Ulhz+";

    // Generates a JWT with a given user's details, claims, and an expiration date.
    public static String generateToken(UserDetails userDetails, Map<String, Object> extraClaims, long expirationDate) {
        return Jwts.builder()
                .setClaims(extraClaims) // Add custom claims, like roles.
                .setSubject(userDetails.getUsername()) // Set the subject (username).
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue date.
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate)) // Set the expiration date.
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key and algorithm.
                .compact(); // Build the token into a compact, URL-safe string.
    }

    // Validates a token against a user's details. Checks for username match and expiration.
    public static boolean isValidToken(String token, UserDetails details) throws MalformedJwtException {
        // Extract username from token and compare it with the user's username.
        boolean isValid = extractUsername(token).equalsIgnoreCase(details.getUsername()) && !isExpired(token);
        if (!isValid) {
            throw new MalformedJwtException("Invalid Token");
        }
        return true;
    }

    // Checks if the token has expired.
    private static boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extracts the expiration date from the token's claims.
    private static Date extractExpiration(String token) throws MalformedJwtException {
        return parseSingleClaim(token, Claims::getExpiration);
    }

    // Extracts the username (subject) from the token's claims.
    public static String extractUsername(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        return parseSingleClaim(token, Claims::getSubject);
    }

    // Extracts a specific claim from the token.
    public static Object extractClaim(String token, String claim) throws MalformedJwtException {
        return parseSingleClaim(token, claims -> claims.get(claim, Object.class));
    }

    // A generic method to parse a single claim from the token.
    private static <T> T parseSingleClaim(String token, Function<Claims, T> resolver) throws ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Extracts all claims from the token by parsing and verifying it.
    private static Claims extractAllClaims(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey()).build();
        return parser.parseClaimsJws(token).getBody();
    }

    // Decodes the secret key from Base64 to a Key object.
    private static Key getSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }
}

package dz.tal.lead.LeadHelper.auth.infrastructure.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import static io.jsonwebtoken.Jwts.SIG; // For standard algorithms like SIG.HS256

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    /**
     * Generates a JWT token for the specified username without using any deprecated methods.
     */
    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                // Modern builder-style methods (no 'setSubject', etc.):
                .subject(username)
                .issuedAt(now)
                .expiration(exp)
                // signWith(Key, SecureDigestAlgorithm) instead of the older
                // signWith(Key, SignatureAlgorithm)
                .signWith(key, SIG.HS256)
                .compact();
    }

    /**
     * Validates the token. Returns true if the token is successfully parsed and not expired.
     */
    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> jws = parseToken(token);
            Date expiration = jws.getPayload().getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            // Malformed, unsupported, signature invalid, etc.
            return false;
        }
    }

    /**
     * Extracts the username (JWT subject) from the token.
     */
    public String extractUsername(String token) {
        Jws<Claims> jws = parseToken(token);
        return jws.getPayload().getSubject();
    }

    /**
     * Parses the token and verifies its signature with the given key.
     * Uses the new verifyWith(...) + parseSignedClaims(...) methods (available in 0.12.x),
     * which return a Jws<Claims> for signed tokens.
     */
    private Jws<Claims> parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // Even though it is named 'parser()', in 0.12.x+ this actually returns a JwtParserBuilder.
        // Then we call 'verifyWith(...)' (instead of setSigningKey(...)) and build + parseSignedClaims(...)
        return Jwts.parser()
                   .verifyWith(key)           // Replaces setSigningKey(...) for clarity
                   .build()
                   .parseSignedClaims(token); // Replaces parseClaimsJws(token)
    }
}
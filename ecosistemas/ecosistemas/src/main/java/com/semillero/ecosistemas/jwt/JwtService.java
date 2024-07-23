    package com.semillero.ecosistemas.jwt;

    import com.semillero.ecosistemas.model.User;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.oauth2.core.user.OAuth2User;

    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;

    @Configuration
    public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration.ms}")
        private long expirationMs;

        public String generateToken(User user) {
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + expirationMs);
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("name", user.getName());
            claims.put("lastName", user.getLastName());
            claims.put("email", user.getEmail());
            claims.put("picture", user.getPicture());
            claims.put("deleted", user.getDeleted());
            claims.put("telephone_number", user.getTelephoneNumber());
            claims.put("role", user.getRole());

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(now)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }

        public Claims extractClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }

        public String extractEmail(String token) {
            return extractClaims(token).getSubject();
        }

        public boolean isTokenExpired(String token) {
            Date expiration = extractClaims(token).getExpiration();
            return expiration.before(new Date());
        }
    }

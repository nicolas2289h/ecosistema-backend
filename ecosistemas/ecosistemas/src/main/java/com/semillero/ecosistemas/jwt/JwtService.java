    package com.semillero.ecosistemas.jwt;

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

        public String generateToken(OAuth2User oAuth2User) {
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + expirationMs);
//            Map<String, Object> claims = new HashMap<>();
//            claims.put("name", oAuth2User.getAttribute("given_name"));
//            claims.put("lastName", oAuth2User.getAttribute("family_name"));
//            claims.put("email", oAuth2User.getAttribute("email"));
//            claims.put("picture", oAuth2User.getAttribute("picture"));
            return Jwts.builder()
//                    .setClaims(claims)
                    .setSubject(oAuth2User.getAttribute("email"))
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

package com.iagomassucato.spring.security.template.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-secret-key";

    public String generateToken(UserDetails userDetails) {
        Date dateNow = new Date();
        Date dateExpiration = new Date(dateNow.getTime() + 86400000);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(dateNow)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}

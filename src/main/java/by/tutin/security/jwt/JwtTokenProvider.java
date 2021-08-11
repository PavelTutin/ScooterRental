package by.tutin.security.jwt;

import by.tutin.model.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.token.secret}")
    private String jwtSecret;
    @Value("${jwt.token.token-validity}")
    private String jwtValidity;

    public String generateToken(User user) {

        Date dateNow = new Date();
        Date validity = new Date(dateNow.getTime() + Long.parseLong(jwtValidity));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, user.getRole())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .setIssuedAt(dateNow)
                .setExpiration(validity)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warning("JWT token is expired or invalid");
        } catch (Exception e) {
            log.warning(e.getLocalizedMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).
                        toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}

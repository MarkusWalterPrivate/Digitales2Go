package de.fraunhofer.DigiTales2Go.security.jwt;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Role;
import de.fraunhofer.DigiTales2Go.dataStructure.user.AppUser;
import de.fraunhofer.DigiTales2Go.dataStructure.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;


    public String getUserMailFromToken(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(generateKeyFromString(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.debug("Email extracted from Token: {}", claims.getSubject());
        return claims.getSubject();

    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();
        Instant expiration;
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            expiration = now.plus(30, ChronoUnit.DAYS);
        } else {

            expiration = now.plus(1, ChronoUnit.DAYS);
        }
        log.debug("Generating token with mail " + user.getUsername());
        return Jwts.builder()
                .setIssuer("DigiTales2Go.Backend")
                .setSubject(user.getUsername())
                .claim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(generateKeyFromString(jwtSecret))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(generateKeyFromString(jwtSecret)).build().parseClaimsJws(token);
            log.debug("Token is valid");
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    private SecretKey generateKeyFromString(String keyString) {
        return Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
    }
}

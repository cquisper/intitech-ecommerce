package com.cquisper.msvc.auth.jwt;

import com.cquisper.msvc.auth.dto.UserAuthenticate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service @Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final Environment env;

    public String generateToken(Map<String, Object> extraClaims, UserAuthenticate userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.email())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Mili, seg, min, hora....
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 1 Dia
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(UserAuthenticate userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.email())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Mili, seg, min, hora....
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 72))) // 3 Dia
                .signWith(getSignInKey())
                .compact();
    }

    public String generateToken(UserAuthenticate usuario){
        return generateToken(new HashMap<>(), usuario);
    }

    public boolean isTokenValid(String token, UserAuthenticate usuario){
        final String username = extractUserEmail(token);
        return (username.equals(usuario.email())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        // True si la fecha del token ya expiro con respecto a la fecha actual, false caso contrario
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String extractUserEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) throws IOException {
        var authoritiesJson = getClaim(token, claims -> claims.get("authorities"));

        log.info(authoritiesJson.toString());

        List<String> roles = new ObjectMapper().readValue(authoritiesJson.toString(), new TypeReference<>() {});

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT_SECRET"));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

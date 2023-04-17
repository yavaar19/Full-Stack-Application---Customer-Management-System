package com.yavaarnosi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.*;

@Service
public class JWTUtil {

    private static final String SECRET_KEY = "2646294A404E635266556A586E327234753778214125442A472D4B6150645367";

    public String issueToken(String subject) {

        return issueToken(subject, Map.of());

    }

    public String issueToken(String subject, String ...scopes) {

        return issueToken(subject, Map.of("scopes", scopes));

    }

    public String issueToken(String subject, List<String> scopes) {

        return issueToken(subject, Map.of("scopes", scopes));

    }

    public String issueToken(String subject, Map<String, Object> claims) {

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://github.com/yavaar19")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(

                        Date.from(Instant.now().plus(15, DAYS))

                )

                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;

    }

    public String getSubject(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

    private Claims getClaims(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;

    }

    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    }

    public boolean isTokenValid(String jwt, String username) {

        String subject = getSubject(jwt);

        return subject.equals(username) && !isTokenExpired(jwt);

    }

    private boolean isTokenExpired(String jwt) {

        Date today = Date.from(Instant.now());

        return getClaims(jwt).getExpiration().before(today);

    }

}
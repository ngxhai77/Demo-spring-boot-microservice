package com.example.authenticationservice.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    private Key key;

    @PostConstruct
    public void postConstruct(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }





    public String generate(String userId,String role, String tokenType){
        Map<String, String> claims = Map.of("id", userId, "role", role);
        long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(expiration) * 1000
                : Long.parseLong(expiration) * 1000 * 5;

        final Date now = new Date();
        final Date exp = new Date(now.getTime() * expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("id"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();

    }


    //get information user login by token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }
    //get userName of token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //get expirationdate token
    public Date getExpirationDate(String token){
        return getClaims(token).getExpiration();
    }

    // for retrieveing any information from token we will need the secret key
    public Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    }

    // check if the token has expired
    public boolean isExpired (String token){
        return getExpirationDate(token).before(new Date());
    }


    //check token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isExpired(token);
    }

}







package com.springboot.springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET = "!@#FEDGSDFGSGSGSHHSHSSHGFFDSSGSSGHS";

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*30))
                .signWith(getSigKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims:: getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Boolean isTokenExpire(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpire(token));
    }
}

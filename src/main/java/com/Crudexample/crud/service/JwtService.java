package com.Crudexample.crud.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "aeac62fb-6c0f-4452-a83b-6751eec100a8";

    public String generateToken(Long userId, String nome,String role) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + (2 * 60 * 60 * 1000));

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("nome", nome)
                .claim("role", role)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) throws JwtException {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

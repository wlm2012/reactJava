package com.study.reactJava.infrastructure.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Slf4j
@Service
public class JwtInfraUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.ttlMillis}")
    private Long ttlMillis;


    public String createToken(String id) {
        // 签名密钥
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        // 设置JWT的签发时间和过期时间
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ttlMillis);
        // 使用指定的密钥和算法生成JWT
        return Jwts.builder()
                .subject(id)
                .issuedAt(now)
                .expiration(expiration) // 设置过期时间
                .signWith(secretKey) // 设置签名密钥和签名算法
                .compact(); // 生成JWT字符串
    }

    public String validateToken(String token) {
        // 签名密钥
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}

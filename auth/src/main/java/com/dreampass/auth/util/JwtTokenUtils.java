package com.dreampass.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JwtTokenUtils {

    private JwtTokenUtils() {

    }

    private static final long EXPIRATION_TIME = 86400000; // 1 天
    // 至少 32 字节，否则抛 WeakKeyException
    private static final String SECRET = "dream-very-secret-key-that-is-long-enough!!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final String CLAIM_KEY_AUTHORITIES = "authorities";

    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(CLAIM_KEY_AUTHORITIES, userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从 token 中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 从 token 中获取权限列表
     */
    public static List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        String authorities = (String) getClaims(token).get(CLAIM_KEY_AUTHORITIES);
        if (authorities == null || authorities.isEmpty()) return Collections.emptyList();
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * 校验 Token 是否过期
     */
    public static boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 校验 Token 是否合法 + 用户一致
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 解析 token 得到 claims
     */
    private static Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // TODO Define dedicated exception
            throw new RuntimeException("Token 无效: " + e.getMessage(), e);
        }
    }
}

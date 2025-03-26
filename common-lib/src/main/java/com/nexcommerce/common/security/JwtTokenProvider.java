package com.nexcommerce.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT令牌提供者
 * 负责JWT令牌的生成、解析和验证
 */
@Slf4j
@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret:defaultSecretKeyWhichShouldBeVeryLongAndSecureForProduction}")
    private String jwtSecret;
    
    @Value("${jwt.expiration.access:86400000}") // 默认1天=86400000毫秒
    private long jwtAccessExpirationMs;
    
    @Value("${jwt.expiration.refresh:604800000}") // 默认7天=604800000毫秒
    private long jwtRefreshExpirationMs;
    
    /**
     * 生成访问令牌
     *
     * @param userId 用户ID
     * @param username 用户名
     * @param authorities 权限列表
     * @return JWT令牌
     */
    public String generateAccessToken(String userId, String username, String authorities) {
        return generateToken(userId, username, authorities, jwtAccessExpirationMs);
    }
    
    /**
     * 生成刷新令牌
     *
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateRefreshToken(String userId, String username) {
        return generateToken(userId, username, null, jwtRefreshExpirationMs);
    }
    
    /**
     * 从令牌中提取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("userId", String.class));
    }
    
    /**
     * 从令牌中提取用户名
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * 从令牌中提取权限
     *
     * @param token JWT令牌
     * @return 权限字符串
     */
    public String getAuthoritiesFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("authorities", String.class));
    }
    
    /**
     * 从令牌中提取过期时间
     *
     * @param token JWT令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    /**
     * 验证令牌是否有效
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    
    /**
     * 生成令牌
     *
     * @param userId 用户ID
     * @param username 用户名
     * @param authorities 权限
     * @param expirationMs 过期时间（毫秒）
     * @return JWT令牌
     */
    private String generateToken(String userId, String username, String authorities, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        if (authorities != null) {
            claims.put("authorities", authorities);
        }
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    /**
     * 从令牌中提取声明
     *
     * @param token JWT令牌
     * @param claimsResolver 声明解析器
     * @param <T> 返回类型
     * @return 声明值
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * 从令牌中提取所有声明
     *
     * @param token JWT令牌
     * @return 所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * 获取签名密钥
     *
     * @return 签名密钥
     */
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

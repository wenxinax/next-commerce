package com.nexcommerce.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

/**
 * JWT认证过滤器
 * 用于验证请求中的JWT令牌并提取用户信息
 */
@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:defaultSecretKeyWhichShouldBeVeryLongAndSecureForProduction}")
    private String jwtSecret;

    // 不需要验证令牌的公共路径
    private final List<String> publicPaths = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh-token",
            "/api/auth/forgot-password",
            "/api/products",
            "/api/categories",
            "/api/brands"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 检查是否为公共路径，如果是则跳过验证
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // 从请求头中获取令牌
        String token = getTokenFromRequest(request);
        if (token == null) {
            return onError(exchange, "未提供令牌", HttpStatus.UNAUTHORIZED);
        }

        try {
            // 验证令牌并提取用户信息
            Claims claims = extractClaims(token);
            String userId = claims.get("userId", String.class);
            String username = claims.getSubject();

            // 将用户信息添加到请求头中，传递给下游服务
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-Username", username)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            log.error("JWT令牌验证失败: {}", e.getMessage());
            return onError(exchange, "无效的令牌", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public int getOrder() {
        // 确保此过滤器在CORS等其他过滤器之后运行
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    /**
     * 从请求中获取令牌
     *
     * @param request HTTP请求
     * @return 令牌字符串或null
     */
    private String getTokenFromRequest(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * 提取令牌中的声明
     *
     * @param token JWT令牌
     * @return 声明
     */
    private Claims extractClaims(String token) {
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

    /**
     * 检查是否为公共路径
     *
     * @param path 请求路径
     * @return 是否为公共路径
     */
    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }

    /**
     * 处理错误响应
     *
     * @param exchange ServerWebExchange
     * @param message 错误消息
     * @param status HTTP状态码
     * @return Mono<Void>
     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}

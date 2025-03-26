package com.nexcommerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置类
 * 定义API网关的路由规则
 */
@Configuration
public class RouteConfiguration {

    /**
     * 配置路由定位器
     * 可以在此处添加额外的路由规则和过滤器
     *
     * @param builder 路由构建器
     * @return 路由定位器
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 用户服务路由
                .route("user-service", r -> r.path("/api/users/**", "/api/auth/**")
                        .uri("lb://user-service"))
                
                // 产品服务路由
                .route("product-service", r -> r.path("/api/products/**", "/api/categories/**", "/api/brands/**")
                        .uri("lb://product-service"))
                
                // 订单服务路由
                .route("order-service", r -> r.path("/api/orders/**")
                        .uri("lb://order-service"))
                
                // 支付服务路由
                .route("payment-service", r -> r.path("/api/payments/**")
                        .uri("lb://payment-service"))
                
                // 购物车服务路由
                .route("cart-service", r -> r.path("/api/cart/**")
                        .uri("lb://cart-service"))
                
                // 通知服务路由
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .uri("lb://notification-service"))
                
                .build();
    }
}

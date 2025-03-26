# nex-commerce

一个基于Spring Cloud的企业级电子商务微服务平台。

## 项目架构

nex-commerce采用微服务架构，包含以下核心服务：

- **api-gateway**: API网关，负责请求路由和过滤
- **config-server**: 配置中心，集中管理各服务配置
- **discovery-service**: 服务发现，负责服务注册与发现
- **user-service**: 用户服务，处理用户认证和个人信息管理
- **product-service**: 产品服务，管理产品目录和库存
- **order-service**: 订单服务，处理订单创建和管理
- **payment-service**: 支付服务，处理支付和退款
- **cart-service**: 购物车服务，管理用户购物车
- **notification-service**: 通知服务，处理系统通知和邮件发送
- **common-lib**: 公共库，包含共享的工具类和DTO

## 技术栈

- Java 17
- Spring Boot 3.x
- Spring Cloud
- Spring Data JPA
- Spring Security
- MySQL
- Redis
- RabbitMQ
- Docker
- Maven

## 启动项目

1. 启动必要的基础设施（使用Docker Compose）：

```bash
cd nex-commerce
docker-compose up -d
```

2. 按以下顺序启动服务：

```
1. discovery-service
2. config-server
3. api-gateway
4. 其他服务（user-service, product-service等）
```

每个服务可以使用以下命令启动：

```bash
cd [service-name]
./mvnw spring-boot:run
```

## API文档

启动项目后，可以通过以下URL访问API文档：

- API网关Swagger文档：http://localhost:8080/swagger-ui.html
- 各服务的Swagger文档：http://localhost:[SERVICE_PORT]/swagger-ui.html

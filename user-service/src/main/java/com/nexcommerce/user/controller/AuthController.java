package com.nexcommerce.user.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.user.dto.JwtAuthResponse;
import com.nexcommerce.user.dto.LoginRequest;
import com.nexcommerce.user.dto.SignupRequest;
import com.nexcommerce.user.dto.UserDto;
import com.nexcommerce.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理用户注册、登录、令牌刷新等操作
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * 用户注册
     *
     * @param signupRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody SignupRequest signupRequest) {
        log.info("新用户注册请求: {}", signupRequest.getUsername());
        UserDto userDto = authService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("用户注册成功")
                        .data(userDto)
                        .build());
    }

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return JWT认证响应
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("用户登录请求: {}", loginRequest.getUsername());
        JwtAuthResponse jwtAuthResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.<JwtAuthResponse>builder()
                .status(HttpStatus.OK.value())
                .message("登录成功")
                .data(jwtAuthResponse)
                .build());
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的JWT认证响应
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> refreshToken(@RequestParam String refreshToken) {
        log.info("刷新令牌请求");
        JwtAuthResponse jwtAuthResponse = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.<JwtAuthResponse>builder()
                .status(HttpStatus.OK.value())
                .message("令牌刷新成功")
                .data(jwtAuthResponse)
                .build());
    }

    /**
     * 用户注销
     *
     * @param userId 用户ID
     * @return 注销结果
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestParam Long userId) {
        log.info("用户注销请求, ID: {}", userId);
        authService.logoutUser(userId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("注销成功")
                .build());
    }
}

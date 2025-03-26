package com.nexcommerce.user.service;

import com.nexcommerce.user.dto.JwtAuthResponse;
import com.nexcommerce.user.dto.LoginRequest;
import com.nexcommerce.user.dto.SignupRequest;
import com.nexcommerce.user.dto.UserDto;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户注册
     *
     * @param signupRequest 注册请求
     * @return 注册后的用户DTO
     */
    UserDto registerUser(SignupRequest signupRequest);

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return JWT认证响应
     */
    JwtAuthResponse loginUser(LoginRequest loginRequest);

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的JWT认证响应
     */
    JwtAuthResponse refreshToken(String refreshToken);

    /**
     * 注销用户
     *
     * @param userId 用户ID
     */
    void logoutUser(Long userId);
}

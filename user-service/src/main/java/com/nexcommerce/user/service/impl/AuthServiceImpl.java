package com.nexcommerce.user.service.impl;

import com.nexcommerce.common.exception.BadRequestException;
import com.nexcommerce.common.exception.UnauthorizedException;
import com.nexcommerce.user.dto.JwtAuthResponse;
import com.nexcommerce.user.dto.LoginRequest;
import com.nexcommerce.user.dto.SignupRequest;
import com.nexcommerce.user.dto.UserDto;
import com.nexcommerce.user.dto.mapper.UserMapper;
import com.nexcommerce.user.model.Role;
import com.nexcommerce.user.model.User;
import com.nexcommerce.user.repository.RoleRepository;
import com.nexcommerce.user.repository.UserRepository;
import com.nexcommerce.user.security.JwtTokenProvider;
import com.nexcommerce.user.service.AuthService;
import com.nexcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto registerUser(SignupRequest signupRequest) {
        log.info("注册新用户: {}", signupRequest.getUsername());

        // 检查用户名是否已存在
        if (userService.existsByUsername(signupRequest.getUsername())) {
            throw new BadRequestException("用户名已被使用");
        }

        // 检查邮箱是否已存在
        if (userService.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("邮箱已被使用");
        }

        // 创建新用户
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .phone(signupRequest.getPhone())
                .status(User.UserStatus.ACTIVE)
                .build();

        // 分配角色
        Set<Role> roles = new HashSet<>();
        if (signupRequest.getRoles() != null && !signupRequest.getRoles().isEmpty()) {
            signupRequest.getRoles().forEach(roleName -> {
                try {
                    Role.RoleName enumRole = Role.RoleName.valueOf(roleName);
                    roleRepository.findByName(enumRole).ifPresent(roles::add);
                } catch (IllegalArgumentException e) {
                    log.warn("无效的角色名: {}", roleName);
                }
            });
        }

        // 如果没有指定角色或指定的角色无效，则分配默认的用户角色
        if (roles.isEmpty()) {
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("默认角色不存在"));
            roles.add(userRole);
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public JwtAuthResponse loginUser(LoginRequest loginRequest) {
        log.info("用户登录: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取用户详情
        org.springframework.security.core.userdetails.UserDetails userDetails = 
                (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
        
        // 获取用户角色
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 获取用户信息
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UnauthorizedException("用户不存在"));

        // 更新最后登录时间
        userService.updateLastLoginTime(user.getId());

        // 生成令牌
        String accessToken = tokenProvider.generateAccessToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationInSeconds())
                .user(userMapper.toDto(user))
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public JwtAuthResponse refreshToken(String refreshToken) {
        log.info("刷新令牌");

        // 验证刷新令牌
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("无效的刷新令牌");
        }

        // 从令牌中获取用户名
        String username = tokenProvider.getUsernameFromToken(refreshToken);

        // 获取用户详情
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("用户不存在"));

        // 创建用户详情对象以生成新令牌
        org.springframework.security.core.userdetails.UserDetails userDetails = 
                org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName())
                        .collect(Collectors.toList())
                        .toArray(new String[0]))
                .build();

        // 生成新的访问令牌
        String newAccessToken = tokenProvider.generateAccessToken(userDetails);

        // 获取用户角色
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        return JwtAuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // 返回相同的刷新令牌
                .tokenType("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationInSeconds())
                .user(userMapper.toDto(user))
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public void logoutUser(Long userId) {
        log.info("用户注销，ID: {}", userId);
        // 由于JWT是无状态的，服务器端不需要特别处理
        // 如果实现了令牌黑名单，这里可以将令牌加入黑名单
    }
}

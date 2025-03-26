package com.nexcommerce.user.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.user.dto.UserDto;
import com.nexcommerce.user.model.User;
import com.nexcommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 处理用户信息查询、更新等操作
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        log.info("获取所有用户请求");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取用户列表成功")
                .data(users)
                .build());
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        log.info("获取用户请求，ID: {}", id);
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.<UserDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取用户成功")
                .data(user)
                .build());
    }

    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param userDto 用户DTO
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        log.info("更新用户请求，ID: {}", id);
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(ApiResponse.<UserDto>builder()
                .status(HttpStatus.OK.value())
                .message("用户更新成功")
                .data(updatedUser)
                .build());
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        log.info("删除用户请求，ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("用户删除成功")
                .build());
    }

    /**
     * 修改用户状态
     *
     * @param id 用户ID
     * @param status 用户状态
     * @return 更新后的用户信息
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDto>> updateUserStatus(@PathVariable Long id, @RequestParam User.UserStatus status) {
        log.info("更新用户状态请求，ID: {}, 状态: {}", id, status);
        UserDto updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(ApiResponse.<UserDto>builder()
                .status(HttpStatus.OK.value())
                .message("用户状态更新成功")
                .data(updatedUser)
                .build());
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 检查结果
     */
    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameAvailability(@RequestParam String username) {
        log.info("检查用户名可用性请求: {}", username);
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(exists ? "用户名已存在" : "用户名可用")
                .data(!exists)
                .build());
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 检查结果
     */
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailAvailability(@RequestParam String email) {
        log.info("检查邮箱可用性请求: {}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(exists ? "邮箱已存在" : "邮箱可用")
                .data(!exists)
                .build());
    }
}

package com.nexcommerce.user.service;

import com.nexcommerce.user.dto.UserDto;
import com.nexcommerce.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userDto 用户DTO
     * @return 创建后的用户DTO
     */
    UserDto createUser(UserDto userDto);

    /**
     * 更新用户
     *
     * @param userId 用户ID
     * @param userDto 用户DTO
     * @return 更新后的用户DTO
     */
    UserDto updateUser(Long userId, UserDto userDto);

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 用户DTO
     */
    UserDto getUserById(Long userId);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户DTO
     */
    UserDto getUserByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户DTO
     */
    UserDto getUserByEmail(String email);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param usernameOrEmail 用户名或邮箱
     * @return 用户Optional
     */
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    /**
     * 获取所有用户
     *
     * @return 用户DTO列表
     */
    List<UserDto> getAllUsers();

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 更新后的用户DTO
     */
    UserDto updateUserStatus(Long userId, User.UserStatus status);

    /**
     * 更新用户最后登录时间
     *
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);
}

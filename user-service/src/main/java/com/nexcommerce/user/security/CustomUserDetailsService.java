package com.nexcommerce.user.security;

import com.nexcommerce.user.model.Role;
import com.nexcommerce.user.model.User;
import com.nexcommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义用户详情服务
 * 实现Spring Security的UserDetailsService接口
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 根据用户名加载用户
     *
     * @param usernameOrEmail 用户名或邮箱
     * @return UserDetails
     * @throws UsernameNotFoundException 如果用户不存在
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // 根据用户名或邮箱查找用户
        User user = userRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> userRepository.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("未找到用户: " + usernameOrEmail)));

        // 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            log.warn("尝试登录非活动用户: {}", usernameOrEmail);
            throw new UsernameNotFoundException("用户账户未激活或已锁定");
        }

        // 获取用户权限
        List<GrantedAuthority> authorities = getAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    /**
     * 从角色集合中提取授权信息
     *
     * @param roles 角色集合
     * @return 授权列表
     */
    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        // 转换角色为授权
        return roles.stream()
                .flatMap(role -> {
                    // 添加角色
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.getName().name()));
                    
                    // 添加权限
                    List<GrantedAuthority> permissions = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .collect(Collectors.toList());
                    
                    return authorities.stream().sequential().collect(Collectors.toList())
                            .stream().sequential().collect(Collectors.toList())
                            .stream().collect(Collectors.toList()).stream();
                })
                .collect(Collectors.toList());
    }
}

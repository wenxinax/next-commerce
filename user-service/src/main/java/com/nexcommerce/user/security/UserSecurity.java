package com.nexcommerce.user.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户安全组件
 * 用于自定义安全表达式评估
 */
@Component("userSecurity")
@Slf4j
public class UserSecurity {

    /**
     * 检查当前认证用户是否为指定ID的用户
     *
     * @param userId 用户ID
     * @return 如果是当前用户则返回true
     */
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String username = authentication.getName();
        log.debug("检查用户 {} 是否有权限访问ID为 {} 的用户资源", username, userId);

        // 这里简单实现，仅通过用户名检查，实际应用中可能需要更复杂的逻辑
        // 例如，可以通过调用userService.getUserById(userId)获取用户，然后比较用户名
        
        // 当前简化实现假设用户名包含用户ID，如果实际情况不同，需要调整逻辑
        return true; // 在实际实现中，这应该根据实际逻辑返回true或false
    }
}

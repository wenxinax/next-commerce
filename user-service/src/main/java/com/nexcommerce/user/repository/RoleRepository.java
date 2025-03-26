package com.nexcommerce.user.repository;

import com.nexcommerce.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 角色存储库接口
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 根据角色名查找角色
     *
     * @param name 角色名
     * @return 角色对象
     */
    Optional<Role> findByName(Role.RoleName name);
}

package com.nexcommerce.user.repository;

import com.nexcommerce.user.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 权限存储库接口
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据权限名查找权限
     *
     * @param name 权限名
     * @return 权限对象
     */
    Optional<Permission> findByName(String name);
}

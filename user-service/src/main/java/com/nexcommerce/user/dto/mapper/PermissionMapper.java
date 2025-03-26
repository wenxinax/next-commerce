package com.nexcommerce.user.dto.mapper;

import com.nexcommerce.user.dto.PermissionDto;
import com.nexcommerce.user.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * 权限实体与DTO转换器
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {

    /**
     * 将权限实体转换为权限DTO
     *
     * @param permission 权限实体
     * @return 权限DTO
     */
    PermissionDto toDto(Permission permission);

    /**
     * 将权限DTO转换为权限实体
     *
     * @param permissionDto 权限DTO
     * @return 权限实体
     */
    @Mapping(target = "roles", ignore = true)
    Permission toEntity(PermissionDto permissionDto);

    /**
     * 将权限实体集合转换为权限DTO集合
     *
     * @param permissions 权限实体集合
     * @return 权限DTO集合
     */
    Set<PermissionDto> toDto(Set<Permission> permissions);

    /**
     * 将权限DTO集合转换为权限实体集合
     *
     * @param permissionDtos 权限DTO集合
     * @return 权限实体集合
     */
    Set<Permission> toEntity(Set<PermissionDto> permissionDtos);
}

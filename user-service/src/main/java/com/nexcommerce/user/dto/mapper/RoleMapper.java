package com.nexcommerce.user.dto.mapper;

import com.nexcommerce.user.dto.RoleDto;
import com.nexcommerce.user.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * 角色实体与DTO转换器
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {

    /**
     * 将角色实体转换为角色DTO
     *
     * @param role 角色实体
     * @return 角色DTO
     */
    @Mapping(target = "name", expression = "java(role.getName().name())")
    RoleDto toDto(Role role);

    /**
     * 将角色DTO转换为角色实体
     *
     * @param roleDto 角色DTO
     * @return 角色实体
     */
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "name", ignore = true)
    Role toEntity(RoleDto roleDto);

    /**
     * 将角色实体集合转换为角色DTO集合
     *
     * @param roles 角色实体集合
     * @return 角色DTO集合
     */
    Set<RoleDto> toDto(Set<Role> roles);

    /**
     * 将角色DTO集合转换为角色实体集合
     *
     * @param roleDtos 角色DTO集合
     * @return 角色实体集合
     */
    Set<Role> toEntity(Set<RoleDto> roleDtos);
}

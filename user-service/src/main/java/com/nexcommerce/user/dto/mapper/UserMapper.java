package com.nexcommerce.user.dto.mapper;

import com.nexcommerce.user.dto.UserDto;
import com.nexcommerce.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 用户实体与DTO转换器
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, AddressMapper.class})
public interface UserMapper {

    /**
     * 将用户实体转换为用户DTO
     *
     * @param user 用户实体
     * @return 用户DTO
     */
    UserDto toDto(User user);

    /**
     * 将用户DTO转换为用户实体
     *
     * @param userDto 用户DTO
     * @return 用户实体
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDto userDto);

    /**
     * 将用户列表转换为用户DTO列表
     *
     * @param users 用户列表
     * @return 用户DTO列表
     */
    List<UserDto> toDto(List<User> users);

    /**
     * 使用用户DTO更新用户实体
     *
     * @param userDto 用户DTO
     * @param user 用户实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}

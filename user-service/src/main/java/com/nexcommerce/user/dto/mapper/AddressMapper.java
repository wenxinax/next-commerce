package com.nexcommerce.user.dto.mapper;

import com.nexcommerce.user.dto.AddressDto;
import com.nexcommerce.user.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

/**
 * 地址实体与DTO转换器
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    /**
     * 将地址实体转换为地址DTO
     *
     * @param address 地址实体
     * @return 地址DTO
     */
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    AddressDto toDto(Address address);

    /**
     * 将地址DTO转换为地址实体
     *
     * @param addressDto 地址DTO
     * @return 地址实体
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Address toEntity(AddressDto addressDto);

    /**
     * 将地址实体集合转换为地址DTO集合
     *
     * @param addresses 地址实体集合
     * @return 地址DTO集合
     */
    Set<AddressDto> toDto(Set<Address> addresses);

    /**
     * 使用地址DTO更新地址实体
     *
     * @param addressDto 地址DTO
     * @param address 地址实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AddressDto addressDto, @MappingTarget Address address);
}

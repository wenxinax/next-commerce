package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.BrandDto;
import com.nexcommerce.product.model.Brand;
import org.mapstruct.*;

import java.util.List;

/**
 * 品牌实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BrandMapper {

    /**
     * 将实体转换为DTO
     *
     * @param brand 品牌实体
     * @return 品牌DTO
     */
    BrandDto toDto(Brand brand);

    /**
     * 将DTO转换为实体
     *
     * @param brandDto 品牌DTO
     * @return 品牌实体
     */
    @Mapping(target = "products", ignore = true)
    Brand toEntity(BrandDto brandDto);

    /**
     * 将实体列表转换为DTO列表
     *
     * @param brands 品牌实体列表
     * @return 品牌DTO列表
     */
    List<BrandDto> toDtoList(List<Brand> brands);

    /**
     * 将DTO列表转换为实体列表
     *
     * @param brandDtos 品牌DTO列表
     * @return 品牌实体列表
     */
    List<Brand> toEntityList(List<BrandDto> brandDtos);

    /**
     * 更新品牌实体
     *
     * @param brandDto 品牌DTO
     * @param brand 目标品牌实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateBrandFromDto(BrandDto brandDto, @MappingTarget Brand brand);
}

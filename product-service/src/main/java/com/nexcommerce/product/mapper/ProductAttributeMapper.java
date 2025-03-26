package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductAttributeDto;
import com.nexcommerce.product.model.ProductAttribute;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

/**
 * 产品属性实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductAttributeMapper {

    /**
     * 将实体转换为DTO
     *
     * @param productAttribute 产品属性实体
     * @return 产品属性DTO
     */
    @Mapping(source = "product.id", target = "productId")
    ProductAttributeDto toDto(ProductAttribute productAttribute);

    /**
     * 将DTO转换为实体
     *
     * @param productAttributeDto 产品属性DTO
     * @return 产品属性实体
     */
    @Mapping(target = "product", ignore = true)
    ProductAttribute toEntity(ProductAttributeDto productAttributeDto);

    /**
     * 将实体集合转换为DTO集合
     *
     * @param productAttributes 产品属性实体集合
     * @return 产品属性DTO集合
     */
    Set<ProductAttributeDto> toDtoSet(Set<ProductAttribute> productAttributes);

    /**
     * 将DTO集合转换为实体集合
     *
     * @param productAttributeDtos 产品属性DTO集合
     * @return 产品属性实体集合
     */
    Set<ProductAttribute> toEntitySet(Set<ProductAttributeDto> productAttributeDtos);

    /**
     * 将实体列表转换为DTO列表
     *
     * @param productAttributes 产品属性实体列表
     * @return 产品属性DTO列表
     */
    List<ProductAttributeDto> toDtoList(List<ProductAttribute> productAttributes);

    /**
     * 将DTO列表转换为实体列表
     *
     * @param productAttributeDtos 产品属性DTO列表
     * @return 产品属性实体列表
     */
    List<ProductAttribute> toEntityList(List<ProductAttributeDto> productAttributeDtos);

    /**
     * 更新产品属性实体
     *
     * @param productAttributeDto 产品属性DTO
     * @param productAttribute 目标产品属性实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProductAttributeFromDto(ProductAttributeDto productAttributeDto, @MappingTarget ProductAttribute productAttribute);
}

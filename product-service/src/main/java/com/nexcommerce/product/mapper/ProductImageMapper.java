package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductImageDto;
import com.nexcommerce.product.model.ProductImage;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

/**
 * 产品图片实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductImageMapper {

    /**
     * 将实体转换为DTO
     *
     * @param productImage 产品图片实体
     * @return 产品图片DTO
     */
    @Mapping(source = "product.id", target = "productId")
    ProductImageDto toDto(ProductImage productImage);

    /**
     * 将DTO转换为实体
     *
     * @param productImageDto 产品图片DTO
     * @return 产品图片实体
     */
    @Mapping(target = "product", ignore = true)
    ProductImage toEntity(ProductImageDto productImageDto);

    /**
     * 将实体集合转换为DTO集合
     *
     * @param productImages 产品图片实体集合
     * @return 产品图片DTO集合
     */
    Set<ProductImageDto> toDtoSet(Set<ProductImage> productImages);

    /**
     * 将DTO集合转换为实体集合
     *
     * @param productImageDtos 产品图片DTO集合
     * @return 产品图片实体集合
     */
    Set<ProductImage> toEntitySet(Set<ProductImageDto> productImageDtos);

    /**
     * 将实体列表转换为DTO列表
     *
     * @param productImages 产品图片实体列表
     * @return 产品图片DTO列表
     */
    List<ProductImageDto> toDtoList(List<ProductImage> productImages);

    /**
     * 将DTO列表转换为实体列表
     *
     * @param productImageDtos 产品图片DTO列表
     * @return 产品图片实体列表
     */
    List<ProductImage> toEntityList(List<ProductImageDto> productImageDtos);

    /**
     * 更新产品图片实体
     *
     * @param productImageDto 产品图片DTO
     * @param productImage 目标产品图片实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProductImageFromDto(ProductImageDto productImageDto, @MappingTarget ProductImage productImage);
}

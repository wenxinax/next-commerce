package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductDto;
import com.nexcommerce.product.model.Product;
import org.mapstruct.*;

import java.util.List;

/**
 * 产品实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", 
        uses = {CategoryMapper.class, BrandMapper.class, ProductImageMapper.class, ProductAttributeMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    /**
     * 将实体转换为DTO
     *
     * @param product 产品实体
     * @return 产品DTO
     */
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "brand.name", target = "brandName")
    ProductDto toDto(Product product);

    /**
     * 将DTO转换为实体
     *
     * @param productDto 产品DTO
     * @return 产品实体
     */
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    Product toEntity(ProductDto productDto);

    /**
     * 将实体列表转换为DTO列表
     *
     * @param products 产品实体列表
     * @return 产品DTO列表
     */
    List<ProductDto> toDtoList(List<Product> products);

    /**
     * 将DTO列表转换为实体列表
     *
     * @param productDtos 产品DTO列表
     * @return 产品实体列表
     */
    List<Product> toEntityList(List<ProductDto> productDtos);

    /**
     * 更新产品实体
     *
     * @param productDto 产品DTO
     * @param product 目标产品实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProductFromDto(ProductDto productDto, @MappingTarget Product product);
}

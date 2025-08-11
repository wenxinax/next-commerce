package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.PromotionDto;
import com.nexcommerce.product.model.Promotion;
import org.mapstruct.*;

import java.util.List;

/**
 * 促销实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PromotionMapper {

    /**
     * 将实体转换为DTO
     */
    @Mapping(target = "applicableProductIds", ignore = true)
    @Mapping(target = "applicableCategoryIds", ignore = true)
    PromotionDto toDto(Promotion promotion);

    /**
     * 将DTO转换为实体
     */
    @Mapping(target = "applicableProducts", ignore = true)
    @Mapping(target = "applicableCategories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Promotion toEntity(PromotionDto promotionDto);

    /**
     * 将实体列表转换为DTO列表
     */
    List<PromotionDto> toDtoList(List<Promotion> promotions);

    /**
     * 更新实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicableProducts", ignore = true)
    @Mapping(target = "applicableCategories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PromotionDto promotionDto, @MappingTarget Promotion promotion);
}

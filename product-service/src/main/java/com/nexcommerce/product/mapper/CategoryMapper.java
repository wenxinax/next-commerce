package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.CategoryDto;
import com.nexcommerce.product.model.Category;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

/**
 * 类别实体与DTO之间的映射接口
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    /**
     * 将实体转换为DTO
     *
     * @param category 类别实体
     * @return 类别DTO
     */
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    CategoryDto toDto(Category category);

    /**
     * 将DTO转换为实体
     *
     * @param categoryDto 类别DTO
     * @return 类别实体
     */
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    /**
     * 将实体列表转换为DTO列表
     *
     * @param categories 类别实体列表
     * @return 类别DTO列表
     */
    List<CategoryDto> toDtoList(List<Category> categories);

    /**
     * 将实体集合转换为DTO集合
     *
     * @param categories 类别实体集合
     * @return 类别DTO集合
     */
    Set<CategoryDto> toDtoSet(Set<Category> categories);

    /**
     * 将DTO列表转换为实体列表
     *
     * @param categoryDtos 类别DTO列表
     * @return 类别实体列表
     */
    List<Category> toEntityList(List<CategoryDto> categoryDtos);

    /**
     * 更新类别实体
     *
     * @param categoryDto 类别DTO
     * @param category 目标类别实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateCategoryFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}

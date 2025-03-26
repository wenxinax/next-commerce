package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.CategoryDto;

import java.util.List;

/**
 * 类别服务接口
 */
public interface CategoryService {

    /**
     * 创建新类别
     *
     * @param categoryDto 类别DTO
     * @return 创建后的类别DTO
     */
    CategoryDto createCategory(CategoryDto categoryDto);

    /**
     * 根据ID获取类别
     *
     * @param id 类别ID
     * @return 类别DTO
     */
    CategoryDto getCategoryById(Long id);

    /**
     * 根据slug获取类别
     *
     * @param slug 类别标识符
     * @return 类别DTO
     */
    CategoryDto getCategoryBySlug(String slug);

    /**
     * 获取所有类别
     *
     * @return 类别DTO列表
     */
    List<CategoryDto> getAllCategories();

    /**
     * 获取所有激活的类别
     *
     * @return 类别DTO列表
     */
    List<CategoryDto> getAllActiveCategories();

    /**
     * 更新类别
     *
     * @param id 类别ID
     * @param categoryDto 类别DTO
     * @return 更新后的类别DTO
     */
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    /**
     * 删除类别
     *
     * @param id 类别ID
     */
    void deleteCategory(Long id);

    /**
     * 获取所有顶级类别（无父类别）
     *
     * @return 类别DTO列表
     */
    List<CategoryDto> getRootCategories();

    /**
     * 获取子类别
     *
     * @param parentId 父类别ID
     * @return 类别DTO列表
     */
    List<CategoryDto> getSubcategories(Long parentId);

    /**
     * 按名称搜索类别
     *
     * @param keyword 关键词
     * @return 类别DTO列表
     */
    List<CategoryDto> searchCategories(String keyword);

    /**
     * 获取带有层级关系的类别树
     *
     * @return 带有子类别的顶级类别DTO列表
     */
    List<CategoryDto> getCategoryTree();

    /**
     * 获取特定类别及其所有子类别
     *
     * @param id 类别ID
     * @return 带有子类别的类别DTO
     */
    CategoryDto getCategoryTreeById(Long id);
}

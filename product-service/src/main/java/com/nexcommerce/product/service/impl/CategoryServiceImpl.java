package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.CategoryDto;
import com.nexcommerce.product.mapper.CategoryMapper;
import com.nexcommerce.product.model.Category;
import com.nexcommerce.product.repository.CategoryRepository;
import com.nexcommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类别服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 创建新类别
     *
     * @param categoryDto 类别DTO
     * @return 创建后的类别DTO
     */
    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("创建新类别: {}", categoryDto.getName());

        // 验证slug是否已存在
        if (categoryRepository.findBySlug(categoryDto.getSlug()).isPresent()) {
            throw new IllegalArgumentException("类别标识已存在: " + categoryDto.getSlug());
        }

        // 转换为实体
        Category category = categoryMapper.toEntity(categoryDto);

        // 设置父类别
        if (categoryDto.getParentId() != null) {
            Category parent = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("父类别不存在，ID: " + categoryDto.getParentId()));
            category.setParent(parent);
        }

        // 保存
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    /**
     * 根据ID获取类别
     *
     * @param id 类别ID
     * @return 类别DTO
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        log.info("获取类别，ID: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("类别不存在，ID: " + id));

        return categoryMapper.toDto(category);
    }

    /**
     * 根据slug获取类别
     *
     * @param slug 类别标识符
     * @return 类别DTO
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryBySlug(String slug) {
        log.info("根据标识获取类别: {}", slug);

        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("类别不存在，标识: " + slug));

        return categoryMapper.toDto(category);
    }

    /**
     * 获取所有类别
     *
     * @return 类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        log.info("获取所有类别");

        List<Category> categories = categoryRepository.findAllByOrderBySortOrderAsc();

        return categoryMapper.toDtoList(categories);
    }

    /**
     * 获取所有激活的类别
     *
     * @return 类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllActiveCategories() {
        log.info("获取所有激活的类别");

        List<Category> activeCategories = categoryRepository.findByIsActiveTrue();

        return categoryMapper.toDtoList(activeCategories);
    }

    /**
     * 更新类别
     *
     * @param id 类别ID
     * @param categoryDto 类别DTO
     * @return 更新后的类别DTO
     */
    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        log.info("更新类别，ID: {}", id);

        // 验证类别是否存在
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("类别不存在，ID: " + id));

        // 如果更新slug，验证是否已存在
        if (categoryDto.getSlug() != null && !categoryDto.getSlug().equals(category.getSlug()) &&
                categoryRepository.findBySlug(categoryDto.getSlug()).isPresent()) {
            throw new IllegalArgumentException("类别标识已存在: " + categoryDto.getSlug());
        }

        // 更新基本属性
        categoryMapper.updateCategoryFromDto(categoryDto, category);

        // 更新父类别
        if (categoryDto.getParentId() != null) {
            // 防止自己成为自己的父级
            if (categoryDto.getParentId().equals(id)) {
                throw new IllegalArgumentException("类别不能成为自己的父类别");
            }

            // 防止循环依赖
            if (isChildCategory(id, categoryDto.getParentId())) {
                throw new IllegalArgumentException("不能将当前类别的子类别设置为其父类别");
            }

            Category parent = categoryRepository.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("父类别不存在，ID: " + categoryDto.getParentId()));
            category.setParent(parent);
        } else if (categoryDto.getParentId() == null && category.getParent() != null) {
            // 移除父类别
            category.setParent(null);
        }

        // 保存更新
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    /**
     * 检查目标类别是否是源类别的子类别（防止循环依赖）
     */
    private boolean isChildCategory(Long sourceId, Long targetId) {
        if (sourceId.equals(targetId)) {
            return true;
        }

        List<Category> children = categoryRepository.findByParentId(sourceId);
        for (Category child : children) {
            if (isChildCategory(child.getId(), targetId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 删除类别
     *
     * @param id 类别ID
     */
    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("删除类别，ID: {}", id);

        // 验证类别是否存在
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("类别不存在，ID: " + id);
        }

        // 检查该类别是否有子类别
        List<Category> children = categoryRepository.findByParentId(id);
        if (!children.isEmpty()) {
            throw new IllegalArgumentException("无法删除类别，请先删除其子类别");
        }

        categoryRepository.deleteById(id);
    }

    /**
     * 获取所有顶级类别（无父类别）
     *
     * @return 类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getRootCategories() {
        log.info("获取所有顶级类别");

        List<Category> rootCategories = categoryRepository.findByParentIsNull();

        return categoryMapper.toDtoList(rootCategories);
    }

    /**
     * 获取子类别
     *
     * @param parentId 父类别ID
     * @return 类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getSubcategories(Long parentId) {
        log.info("获取子类别，父ID: {}", parentId);

        // 验证父类别是否存在
        if (!categoryRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("父类别不存在，ID: " + parentId);
        }

        List<Category> children = categoryRepository.findByParentId(parentId);

        return categoryMapper.toDtoList(children);
    }

    /**
     * 按名称搜索类别
     *
     * @param keyword 关键词
     * @return 类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(String keyword) {
        log.info("搜索类别: {}", keyword);

        List<Category> categories = categoryRepository.findByNameContaining(keyword);

        return categoryMapper.toDtoList(categories);
    }

    /**
     * 获取带有层级关系的类别树
     *
     * @return 带有子类别的顶级类别DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryTree() {
        log.info("获取类别树");

        // 获取所有顶级类别
        List<Category> rootCategories = categoryRepository.findByParentIsNull();

        // 构建每个顶级类别的树
        return rootCategories.stream()
                .map(this::buildCategoryTree)
                .collect(Collectors.toList());
    }

    /**
     * 获取特定类别及其所有子类别
     *
     * @param id 类别ID
     * @return 带有子类别的类别DTO
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryTreeById(Long id) {
        log.info("获取类别树，根ID: {}", id);

        Category category = categoryRepository.findWithChildrenById(id)
                .orElseThrow(() -> new ResourceNotFoundException("类别不存在，ID: " + id));

        return buildCategoryTree(category);
    }

    /**
     * 构建类别树
     *
     * @param category 类别
     * @return 类别DTO（包含子类别）
     */
    private CategoryDto buildCategoryTree(Category category) {
        CategoryDto categoryDto = categoryMapper.toDto(category);

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            List<CategoryDto> childrenDtos = category.getChildren().stream()
                    .map(this::buildCategoryTree)
                    .collect(Collectors.toList());
            categoryDto.setChildren(new java.util.HashSet<>(childrenDtos));
        }

        return categoryDto;
    }
}

package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.CategoryDto;
import com.nexcommerce.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类别控制器
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 创建新类别
     *
     * @param categoryDto 类别DTO
     * @return 创建的类别
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("创建新类别请求: {}", categoryDto.getName());
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CategoryDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("类别创建成功")
                        .data(createdCategory)
                        .build());
    }

    /**
     * 根据ID获取类别
     *
     * @param id 类别ID
     * @return 类别信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        log.info("获取类别请求，ID: {}", id);
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别成功")
                .data(category)
                .build());
    }

    /**
     * 根据slug获取类别
     *
     * @param slug 类别标识符
     * @return 类别信息
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryBySlug(@PathVariable String slug) {
        log.info("根据标识获取类别请求: {}", slug);
        CategoryDto category = categoryService.getCategoryBySlug(slug);
        return ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别成功")
                .data(category)
                .build());
    }

    /**
     * 获取所有类别
     *
     * @return 类别列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        log.info("获取所有类别请求");
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取所有类别成功")
                .data(categories)
                .build());
    }

    /**
     * 获取所有激活的类别
     *
     * @return 类别列表
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllActiveCategories() {
        log.info("获取所有激活的类别请求");
        List<CategoryDto> categories = categoryService.getAllActiveCategories();
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取所有激活类别成功")
                .data(categories)
                .build());
    }

    /**
     * 更新类别
     *
     * @param id 类别ID
     * @param categoryDto 类别DTO
     * @return 更新后的类别
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {
        log.info("更新类别请求，ID: {}", id);
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .status(HttpStatus.OK.value())
                .message("类别更新成功")
                .data(updatedCategory)
                .build());
    }

    /**
     * 删除类别
     *
     * @param id 类别ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        log.info("删除类别请求，ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("类别删除成功")
                .build());
    }

    /**
     * 获取所有顶级类别
     *
     * @return 顶级类别列表
     */
    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getRootCategories() {
        log.info("获取所有顶级类别请求");
        List<CategoryDto> categories = categoryService.getRootCategories();
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取顶级类别成功")
                .data(categories)
                .build());
    }

    /**
     * 获取子类别
     *
     * @param parentId 父类别ID
     * @return 子类别列表
     */
    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getSubcategories(@PathVariable Long parentId) {
        log.info("获取子类别请求，父ID: {}", parentId);
        List<CategoryDto> subcategories = categoryService.getSubcategories(parentId);
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取子类别成功")
                .data(subcategories)
                .build());
    }

    /**
     * 搜索类别
     *
     * @param keyword 搜索关键词
     * @return 类别列表
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> searchCategories(@RequestParam String keyword) {
        log.info("搜索类别请求: {}", keyword);
        List<CategoryDto> categories = categoryService.searchCategories(keyword);
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("类别搜索成功")
                .data(categories)
                .build());
    }

    /**
     * 获取类别树
     *
     * @return 类别树
     */
    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategoryTree() {
        log.info("获取类别树请求");
        List<CategoryDto> categoryTree = categoryService.getCategoryTree();
        return ResponseEntity.ok(ApiResponse.<List<CategoryDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别树成功")
                .data(categoryTree)
                .build());
    }

    /**
     * 获取特定ID的类别树
     *
     * @param id 类别ID
     * @return 类别树
     */
    @GetMapping("/{id}/tree")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryTreeById(@PathVariable Long id) {
        log.info("获取特定ID的类别树请求，ID: {}", id);
        CategoryDto categoryTree = categoryService.getCategoryTreeById(id);
        return ResponseEntity.ok(ApiResponse.<CategoryDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别树成功")
                .data(categoryTree)
                .build());
    }
}

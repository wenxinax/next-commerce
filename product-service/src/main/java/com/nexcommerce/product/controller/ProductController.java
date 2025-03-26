package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.ProductDto;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品控制器
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    /**
     * 创建新产品
     *
     * @param productDto 产品DTO
     * @return 创建的产品
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("创建新产品请求: {}", productDto.getName());
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProductDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("产品创建成功")
                        .data(createdProduct)
                        .build());
    }

    /**
     * 根据ID获取产品
     *
     * @param id 产品ID
     * @return 产品信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long id) {
        log.info("获取产品请求，ID: {}", id);
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品成功")
                .data(product)
                .build());
    }

    /**
     * 根据SKU获取产品
     *
     * @param sku 产品SKU
     * @return 产品信息
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductBySku(@PathVariable String sku) {
        log.info("根据SKU获取产品请求: {}", sku);
        ProductDto product = productService.getProductBySku(sku);
        return ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品成功")
                .data(product)
                .build());
    }

    /**
     * 获取所有产品（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param direction 排序方向
     * @return 产品分页列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        log.info("获取所有产品请求，页码: {}, 每页大小: {}", page, size);
        
        Pageable pageable = PageRequest.of(page, size, 
                Sort.by(Sort.Direction.fromString(direction), sortBy));
        
        Page<ProductDto> products = productService.getAllProducts(pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品列表成功")
                .data(products)
                .build());
    }

    /**
     * 更新产品
     *
     * @param id 产品ID
     * @param productDto 产品DTO
     * @return 更新后的产品
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody ProductDto productDto) {
        log.info("更新产品请求，ID: {}", id);
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK.value())
                .message("产品更新成功")
                .data(updatedProduct)
                .build());
    }

    /**
     * 删除产品
     *
     * @param id 产品ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        log.info("删除产品请求，ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("产品删除成功")
                .build());
    }

    /**
     * 按名称搜索产品
     *
     * @param name 产品名称关键字
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> searchProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("按名称搜索产品请求: {}", name);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.searchProductsByName(name, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("产品搜索成功")
                .data(products)
                .build());
    }

    /**
     * 按类别获取产品
     *
     * @param categoryId 类别ID
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("获取类别下的产品请求，类别ID: {}", categoryId);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsByCategoryId(categoryId, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别产品成功")
                .data(products)
                .build());
    }

    /**
     * 按品牌获取产品
     *
     * @param brandId 品牌ID
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsByBrand(
            @PathVariable Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("获取品牌下的产品请求，品牌ID: {}", brandId);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsByBrandId(brandId, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取品牌产品成功")
                .data(products)
                .build());
    }

    /**
     * 获取特价产品
     *
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/on-sale")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsOnSale(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("获取特价产品请求");
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsOnSale(pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取特价产品成功")
                .data(products)
                .build());
    }

    /**
     * 按价格范围获取产品
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("按价格范围获取产品请求: {} - {}", minPrice, maxPrice);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取价格范围产品成功")
                .data(products)
                .build());
    }

    /**
     * 获取推荐产品
     *
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getFeaturedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("获取推荐产品请求");
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getFeaturedProducts(pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取推荐产品成功")
                .data(products)
                .build());
    }

    /**
     * 按产品状态获取产品
     *
     * @param status 产品状态
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsByStatus(
            @PathVariable Product.ProductStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("按状态获取产品请求: {}", status);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsByStatus(status, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取状态产品成功")
                .data(products)
                .build());
    }

    /**
     * 筛选产品
     *
     * @param categoryId 类别ID（可选）
     * @param brandId 品牌ID（可选）
     * @param minPrice 最低价格（可选）
     * @param maxPrice 最高价格（可选）
     * @param page 页码
     * @param size 每页大小
     * @return 产品分页列表
     */
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("筛选产品请求: 类别ID={}, 品牌ID={}, 价格范围={}-{}", categoryId, brandId, minPrice, maxPrice);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getProductsByFilters(categoryId, brandId, minPrice, maxPrice, pageable);
        
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("筛选产品成功")
                .data(products)
                .build());
    }

    /**
     * 更新产品库存
     *
     * @param id 产品ID
     * @param quantityChange 库存变化（正数增加，负数减少）
     * @return 更新后的产品
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductDto>> updateProductStock(
            @PathVariable Long id,
            @RequestParam Integer quantityChange) {
        log.info("更新产品库存请求，ID: {}, 变化量: {}", id, quantityChange);
        
        ProductDto updatedProduct = productService.updateProductStock(id, quantityChange);
        
        return ResponseEntity.ok(ApiResponse.<ProductDto>builder()
                .status(HttpStatus.OK.value())
                .message("产品库存更新成功")
                .data(updatedProduct)
                .build());
    }

    /**
     * 获取低库存产品
     *
     * @param threshold 库存阈值
     * @return 低库存产品列表
     */
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getLowStockProducts(
            @RequestParam(defaultValue = "5") Integer threshold) {
        log.info("获取低库存产品请求，阈值: {}", threshold);
        
        List<ProductDto> products = productService.getLowStockProducts(threshold);
        
        return ResponseEntity.ok(ApiResponse.<List<ProductDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取低库存产品成功")
                .data(products)
                .build());
    }
}

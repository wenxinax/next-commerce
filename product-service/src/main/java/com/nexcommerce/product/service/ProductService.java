package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.ProductDto;
import com.nexcommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品服务接口
 */
public interface ProductService {

    /**
     * 创建新产品
     *
     * @param productDto 产品DTO
     * @return 创建后的产品DTO
     */
    ProductDto createProduct(ProductDto productDto);

    /**
     * 根据ID获取产品
     *
     * @param id 产品ID
     * @return 产品DTO
     */
    ProductDto getProductById(Long id);

    /**
     * 根据SKU获取产品
     *
     * @param sku 产品SKU
     * @return 产品DTO
     */
    ProductDto getProductBySku(String sku);

    /**
     * 获取所有产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getAllProducts(Pageable pageable);

    /**
     * 更新产品
     *
     * @param id 产品ID
     * @param productDto 产品DTO
     * @return 更新后的产品DTO
     */
    ProductDto updateProduct(Long id, ProductDto productDto);

    /**
     * 删除产品
     *
     * @param id 产品ID
     */
    void deleteProduct(Long id);

    /**
     * 根据名称搜索产品（分页）
     *
     * @param name 产品名称（关键字）
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> searchProductsByName(String name, Pageable pageable);

    /**
     * 根据类别ID获取产品（分页）
     *
     * @param categoryId 类别ID
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable);

    /**
     * 根据品牌ID获取产品（分页）
     *
     * @param brandId 品牌ID
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsByBrandId(Long brandId, Pageable pageable);

    /**
     * 获取特价产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsOnSale(Pageable pageable);

    /**
     * 获取价格在指定范围内的产品（分页）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * 获取推荐产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getFeaturedProducts(Pageable pageable);

    /**
     * 根据状态获取产品（分页）
     *
     * @param status 产品状态
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsByStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * 根据多个条件筛选产品（分页）
     *
     * @param categoryId 类别ID（可选）
     * @param brandId 品牌ID（可选）
     * @param minPrice 最低价格（可选）
     * @param maxPrice 最高价格（可选）
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    Page<ProductDto> getProductsByFilters(Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * 更新产品库存
     *
     * @param id 产品ID
     * @param quantityChange 库存变化（正数增加，负数减少）
     * @return 更新后的产品DTO
     */
    ProductDto updateProductStock(Long id, Integer quantityChange);

    /**
     * 获取低库存产品
     *
     * @param threshold 库存阈值
     * @return 低库存产品列表
     */
    List<ProductDto> getLowStockProducts(Integer threshold);
}

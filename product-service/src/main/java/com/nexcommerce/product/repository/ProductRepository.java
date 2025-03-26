package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 产品存储库接口
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 根据商品SKU查找产品
     *
     * @param sku 商品SKU
     * @return 产品对象
     */
    Optional<Product> findBySku(String sku);

    /**
     * 根据商品名称查找产品（模糊匹配）
     *
     * @param name 商品名称
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    Page<Product> findByNameContaining(String name, Pageable pageable);

    /**
     * 根据类别ID查找产品
     *
     * @param categoryId 类别ID
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /**
     * 根据品牌ID查找产品
     *
     * @param brandId 品牌ID
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findByBrandId(@Param("brandId") Long brandId, Pageable pageable);

    /**
     * 查找特价商品
     *
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    Page<Product> findBySalePriceIsNotNull(Pageable pageable);

    /**
     * 查找价格在指定范围内的产品
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * 查找推荐商品
     *
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    Page<Product> findByIsFeatured(Boolean isFeatured, Pageable pageable);

    /**
     * 根据状态查找产品
     *
     * @param status 产品状态
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * 复杂查询：根据类别ID、品牌ID和价格范围查找产品
     *
     * @param categoryId 类别ID
     * @param brandId 品牌ID
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页对象
     * @return 产品分页列表
     */
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:brandId IS NULL OR p.brand.id = :brandId) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "p.status = 'ACTIVE'")
    Page<Product> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    /**
     * 获取低库存产品
     *
     * @param threshold 库存阈值
     * @return 产品列表
     */
    List<Product> findByQuantityLessThanAndStatusNot(Integer threshold, Product.ProductStatus status);
}

package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 产品图片数据访问接口
 */
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    /**
     * 根据产品ID查找所有图片（按排序顺序）
     */
    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);

    /**
     * 根据产品ID查找主图
     */
    Optional<ProductImage> findByProductIdAndIsPrimaryTrue(Long productId);

    /**
     * 删除产品的所有图片
     */
    void deleteByProductId(Long productId);

    /**
     * 根据图片URL路径查找图片
     */
    Optional<ProductImage> findByImageUrl(String imageUrl);

    /**
     * 获取产品图片数量
     */
    @Query("SELECT COUNT(pi) FROM ProductImage pi WHERE pi.product.id = :productId")
    Long countByProductId(@Param("productId") Long productId);
}

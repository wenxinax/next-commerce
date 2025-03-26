package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 品牌存储库接口
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * 根据品牌名称查找品牌
     *
     * @param name 品牌名称
     * @return 品牌对象
     */
    Optional<Brand> findByName(String name);

    /**
     * 根据品牌slug查找品牌
     *
     * @param slug 品牌slug
     * @return 品牌对象
     */
    Optional<Brand> findBySlug(String slug);

    /**
     * 查找激活状态的品牌
     *
     * @return 激活状态的品牌列表
     */
    List<Brand> findByIsActiveTrue();

    /**
     * 查找包含指定关键字的品牌
     *
     * @param keyword 关键字
     * @return 品牌列表
     */
    List<Brand> findByNameContaining(String keyword);

    /**
     * 根据排序顺序查找品牌
     *
     * @return 排序后的品牌列表
     */
    List<Brand> findAllByOrderBySortOrderAsc();
}

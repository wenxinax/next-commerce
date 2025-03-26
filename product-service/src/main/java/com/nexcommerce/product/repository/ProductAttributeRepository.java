package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 产品属性数据访问接口
 */
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    /**
     * 根据产品ID查找所有属性
     */
    List<ProductAttribute> findByProductId(Long productId);

    /**
     * 根据产品ID和属性名查找属性
     */
    Optional<ProductAttribute> findByProductIdAndAttributeName(Long productId, String attributeName);

    /**
     * 根据属性名和属性值查找属性
     */
    List<ProductAttribute> findByAttributeNameAndAttributeValue(String attributeName, String attributeValue);

    /**
     * 删除产品的所有属性
     */
    void deleteByProductId(Long productId);

    /**
     * 获取指定属性名的所有不同值
     */
    @Query("SELECT DISTINCT pa.attributeValue FROM ProductAttribute pa WHERE pa.attributeName = :attributeName")
    List<String> findDistinctAttributeValuesByAttributeName(@Param("attributeName") String attributeName);
}

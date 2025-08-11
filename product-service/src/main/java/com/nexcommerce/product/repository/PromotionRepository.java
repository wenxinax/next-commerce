package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 促销活动数据访问接口
 */
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByIsActiveTrue();
    
    Optional<Promotion> findByCode(String code);
    
    @Query("SELECT p FROM Promotion p WHERE p.isActive = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p JOIN p.applicableProducts pr WHERE pr.id = :productId AND p.isActive = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotionsForProduct(@Param("productId") Long productId, @Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p JOIN p.applicableCategories c WHERE c.id = :categoryId AND p.isActive = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotionsForCategory(@Param("categoryId") Long categoryId, @Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.type = :type AND p.isActive = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotionsByType(@Param("type") String type, @Param("now") LocalDateTime now);
}

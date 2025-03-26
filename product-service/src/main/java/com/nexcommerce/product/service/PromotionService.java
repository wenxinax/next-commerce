package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.PromotionDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 促销服务接口
 */
public interface PromotionService {

    /**
     * 创建新促销活动
     *
     * @param promotionDto 促销DTO
     * @return 创建后的促销DTO
     */
    PromotionDto createPromotion(PromotionDto promotionDto);

    /**
     * 获取促销活动
     *
     * @param id 促销ID
     * @return 促销DTO
     */
    PromotionDto getPromotion(Long id);

    /**
     * 获取所有促销活动
     *
     * @return 促销DTO列表
     */
    List<PromotionDto> getAllPromotions();

    /**
     * 获取所有激活的促销活动
     *
     * @return 促销DTO列表
     */
    List<PromotionDto> getActivePromotions();

    /**
     * 更新促销活动
     *
     * @param id 促销ID
     * @param promotionDto 促销DTO
     * @return 更新后的促销DTO
     */
    PromotionDto updatePromotion(Long id, PromotionDto promotionDto);

    /**
     * 激活促销活动
     *
     * @param id 促销ID
     * @return 更新后的促销DTO
     */
    PromotionDto activatePromotion(Long id);

    /**
     * 禁用促销活动
     *
     * @param id 促销ID
     * @return 更新后的促销DTO
     */
    PromotionDto deactivatePromotion(Long id);

    /**
     * 删除促销活动
     *
     * @param id 促销ID
     */
    void deletePromotion(Long id);

    /**
     * 获取产品适用的所有促销活动
     *
     * @param productId 产品ID
     * @return 促销DTO列表
     */
    List<PromotionDto> getPromotionsForProduct(Long productId);

    /**
     * 获取类别适用的所有促销活动
     *
     * @param categoryId 类别ID
     * @return 促销DTO列表
     */
    List<PromotionDto> getPromotionsForCategory(Long categoryId);

    /**
     * 应用促销码
     *
     * @param code 促销码
     * @param subtotal 订单小计
     * @return 应用促销后的金额
     */
    BigDecimal applyPromotionCode(String code, BigDecimal subtotal);

    /**
     * 计算产品折扣价格
     *
     * @param productId 产品ID
     * @param originalPrice 原价
     * @return 折扣后价格
     */
    BigDecimal calculateDiscountedPrice(Long productId, BigDecimal originalPrice);
    
    /**
     * 验证促销码
     *
     * @param code 促销码
     * @return 是否有效
     */
    boolean isValidPromotionCode(String code);
    
    /**
     * 创建闪购活动
     * 
     * @param productIds 产品ID列表
     * @param discountRate 折扣率
     * @param hours 持续时间（小时）
     * @return 创建的促销活动
     */
    PromotionDto CreateFlashSale(List<Long> productIds, BigDecimal discountRate, int hours);
}

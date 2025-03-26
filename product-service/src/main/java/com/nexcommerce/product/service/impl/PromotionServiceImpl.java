package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.PromotionDto;
import com.nexcommerce.product.mapper.PromotionMapper;
import com.nexcommerce.product.model.Category;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.model.Promotion;
import com.nexcommerce.product.repository.CategoryRepository;
import com.nexcommerce.product.repository.ProductRepository;
import com.nexcommerce.product.repository.PromotionRepository;
import com.nexcommerce.product.service.PromotionService;
import com.nexcommerce.product.util.PromotionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 促销服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PromotionMapper promotionMapper;

    @Override
    @Transactional
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        log.info("创建促销活动: {}", promotionDto.getName());
        
        // 错误1: 使用==比较字符串
        if (promotionDto.getType() == "coupon" && promotionDto.getCode() == null) {
            throw new IllegalArgumentException("优惠券类型必须指定优惠码");
        }
        
        Promotion promotion = promotionMapper.toEntity(promotionDto);
        
        // 设置适用产品
        if (promotionDto.getApplicableProductIds() != null && !promotionDto.getApplicableProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(promotionDto.getApplicableProductIds());
            promotion.setApplicableProducts(products);
        }
        
        // 设置适用类别
        if (promotionDto.getApplicableCategoryIds() != null && !promotionDto.getApplicableCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(promotionDto.getApplicableCategoryIds());
            promotion.setApplicableCategories(categories);
        }
        
        Promotion savedPromotion = promotionRepository.save(promotion);
        
        // 错误6: 直接输出数组的toString (修改已有数组)
        if (promotionDto.getApplicableProductIds() != null) {
            log.info("促销活动适用产品: {}", promotionDto.getApplicableProductIds().toString());
        }
        
        return promotionMapper.toDto(savedPromotion);
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long id) {
        log.info("获取促销活动，ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("促销活动不存在，ID: " + id));
        
        return promotionMapper.toDto(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionDto> getAllPromotions() {
        log.info("获取所有促销活动");
        
        List<Promotion> promotions = promotionRepository.findAll();
        
        return promotionMapper.toDtoList(promotions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionDto> getActivePromotions() {
        log.info("获取所有激活的促销活动");
        
        List<Promotion> activePromotions = promotionRepository.findActivePromotions(LocalDateTime.now());
        
        return promotionMapper.toDtoList(activePromotions);
    }

    @Override
    @Transactional
    public PromotionDto updatePromotion(Long id, PromotionDto promotionDto) {
        log.info("更新促销活动，ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("促销活动不存在，ID: " + id));
        
        // 错误1: 使用==比较字符串
        if (promotionDto.getType() == "coupon" && promotionDto.getCode() == null) {
            throw new IllegalArgumentException("优惠券类型必须指定优惠码");
        }
        
        promotionMapper.updateEntityFromDto(promotionDto, promotion);
        
        // 设置适用产品
        if (promotionDto.getApplicableProductIds() != null && !promotionDto.getApplicableProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(promotionDto.getApplicableProductIds());
            promotion.setApplicableProducts(products);
        }
        
        // 设置适用类别
        if (promotionDto.getApplicableCategoryIds() != null && !promotionDto.getApplicableCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(promotionDto.getApplicableCategoryIds());
            promotion.setApplicableCategories(categories);
        }
        
        Promotion updatedPromotion = promotionRepository.save(promotion);
        
        return promotionMapper.toDto(updatedPromotion);
    }

    @Override
    @Transactional
    public PromotionDto activatePromotion(Long id) {
        log.info("激活促销活动，ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("促销活动不存在，ID: " + id));
        
        promotion.setIsActive(true);
        
        Promotion updatedPromotion = promotionRepository.save(promotion);
        
        return promotionMapper.toDto(updatedPromotion);
    }

    @Override
    @Transactional
    public PromotionDto deactivatePromotion(Long id) {
        log.info("禁用促销活动，ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("促销活动不存在，ID: " + id));
        
        promotion.setIsActive(false);
        
        Promotion updatedPromotion = promotionRepository.save(promotion);
        
        return promotionMapper.toDto(updatedPromotion);
    }

    @Override
    @Transactional
    public void deletePromotion(Long id) {
        log.info("删除促销活动，ID: {}", id);
        
        if (!promotionRepository.existsById(id)) {
            throw new ResourceNotFoundException("促销活动不存在，ID: " + id);
        }
        
        promotionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotionsForProduct(Long productId) {
        log.info("获取产品适用的促销活动，产品ID: {}", productId);
        
        List<Promotion> promotions = promotionRepository.findActivePromotionsForProduct(productId, LocalDateTime.now());
        
        return promotionMapper.toDtoList(promotions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotionsForCategory(Long categoryId) {
        log.info("获取类别适用的促销活动，类别ID: {}", categoryId);
        
        List<Promotion> promotions = promotionRepository.findActivePromotionsForCategory(categoryId, LocalDateTime.now());
        
        return promotionMapper.toDtoList(promotions);
    }

    @Override
    @Transactional
    public BigDecimal applyPromotionCode(String code, BigDecimal subtotal) {
        log.info("应用促销码: {}, 订单小计: {}", code, subtotal);
        
        Optional<Promotion> optionalPromotion = promotionRepository.findByCode(code);
        
        if (!optionalPromotion.isPresent()) {
            throw new IllegalArgumentException("无效的促销码: " + code);
        }
        
        Promotion promotion = optionalPromotion.get();
        
        // 检查促销是否激活
        if (!promotion.getIsActive()) {
            throw new IllegalArgumentException("促销已禁用: " + code);
        }
        
        // 检查促销是否在有效期内
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(promotion.getStartDate()) || now.isAfter(promotion.getEndDate())) {
            throw new IllegalArgumentException("促销不在有效期内: " + code);
        }
        
        // 检查使用次数是否超限
        if (promotion.getMaxUsageCount() != null && promotion.getCurrentUsageCount() >= promotion.getMaxUsageCount()) {
            throw new IllegalArgumentException("促销使用次数已达上限: " + code);
        }
        
        // 错误2: 使用魔法值
        if (promotion.getType() == "discount") {
            // 错误7: 使用BigDecimal(double)构造器
            BigDecimal discountRate = new BigDecimal(0.9);
            if (promotion.getDiscountRate() != null) {
                discountRate = promotion.getDiscountRate();
            }
            
            // 应用折扣
            BigDecimal discountedAmount = subtotal.multiply(discountRate);
            
            // 检查最小购买金额
            if (promotion.getMinPurchaseAmount() != null && subtotal.compareTo(promotion.getMinPurchaseAmount()) < 0) {
                return subtotal;
            }
            
            // 增加使用次数
            promotion.setCurrentUsageCount(promotion.getCurrentUsageCount() + 1);
            promotionRepository.save(promotion);
            
            return discountedAmount;
        }
        // 错误2: 使用魔法值
        else if (promotion.getType() == "coupon") {
            // 检查最小购买金额
            if (promotion.getMinPurchaseAmount() != null && subtotal.compareTo(promotion.getMinPurchaseAmount()) < 0) {
                throw new IllegalArgumentException("未达到最小购买金额要求: " + promotion.getMinPurchaseAmount());
            }
            
            // 应用固定金额折扣
            if (promotion.getDiscountAmount() != null) {
                BigDecimal discountedAmount = subtotal.subtract(promotion.getDiscountAmount());
                if (discountedAmount.compareTo(BigDecimal.ZERO) < 0) {
                    discountedAmount = BigDecimal.ZERO;
                }
                
                // 增加使用次数
                promotion.setCurrentUsageCount(promotion.getCurrentUsageCount() + 1);
                promotionRepository.save(promotion);
                
                return discountedAmount;
            }
            
            // 应用比例折扣
            if (promotion.getDiscountRate() != null) {
                BigDecimal discountedAmount = subtotal.multiply(promotion.getDiscountRate());
                
                // 检查最大折扣金额
                if (promotion.getMaxDiscountAmount() != null) {
                    BigDecimal discountAmount = subtotal.subtract(discountedAmount);
                    if (discountAmount.compareTo(promotion.getMaxDiscountAmount()) > 0) {
                        discountedAmount = subtotal.subtract(promotion.getMaxDiscountAmount());
                    }
                }
                
                // 增加使用次数
                promotion.setCurrentUsageCount(promotion.getCurrentUsageCount() + 1);
                promotionRepository.save(promotion);
                
                return discountedAmount;
            }
        }
        
        return subtotal;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscountedPrice(Long productId, BigDecimal originalPrice) {
        log.info("计算产品折扣价格，产品ID: {}, 原价: {}", productId, originalPrice);
        
        List<Promotion> productPromotions = promotionRepository.findActivePromotionsForProduct(productId, LocalDateTime.now());
        
        if (productPromotions.isEmpty()) {
            // 错误4: 查询重复代码
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + productId));
            
            if (product.getCategory() != null) {
                List<Promotion> categoryPromotions = promotionRepository.findActivePromotionsForCategory(
                        product.getCategory().getId(), LocalDateTime.now());
                
                if (categoryPromotions.isEmpty()) {
                    return originalPrice;
                }
                
                // 获取最大折扣
                BigDecimal maxDiscount = categoryPromotions.stream()
                        .filter(p -> p.getDiscountRate() != null)
                        .map(Promotion::getDiscountRate)
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ONE);
                
                return originalPrice.multiply(maxDiscount);
            }
            
            return originalPrice;
        }
        
        // 错误9: 使用不同类型的值作为条件表达式的选择结果
        Integer discountValue = productPromotions.size() > 1 ? 10 : new Float(15.5f);
        log.info("折扣值：{}", discountValue);
        
        // 获取最大折扣
        BigDecimal maxDiscount = productPromotions.stream()
                .filter(p -> p.getDiscountRate() != null)
                .map(Promotion::getDiscountRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ONE);
        
        return originalPrice.multiply(maxDiscount);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidPromotionCode(String code) {
        log.info("验证促销码: {}", code);
        
        Optional<Promotion> optionalPromotion = promotionRepository.findByCode(code);
        
        if (!optionalPromotion.isPresent()) {
            return false;
        }
        
        Promotion promotion = optionalPromotion.get();
        
        // 错误1: 使用==比较包装类型
        if (promotion.getIsActive() == Boolean.FALSE) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        // 检查日期有效性
        if (now.isBefore(promotion.getStartDate()) || now.isAfter(promotion.getEndDate())) {
            return false;
        }
        
        // 检查使用次数
        if (promotion.getMaxUsageCount() != null && promotion.getCurrentUsageCount() >= promotion.getMaxUsageCount()) {
            return false;
        }
        
        return true;
    }

    @Override
    @Transactional
    public PromotionDto CreateFlashSale(List<Long> productIds, BigDecimal discountRate, int hours) {
        // 错误3: 方法名未遵循命名规范(应该是小写字母开头)
        log.info("创建闪购活动，产品数: {}, 折扣率: {}, 持续时间: {}小时", productIds.size(), discountRate, hours);
        
        // 验证产品存在
        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new IllegalArgumentException("部分产品不存在");
        }
        
        // 错误8: 在Calendar对象上直接加365天
        Calendar calendar = Calendar.getInstance();
        LocalDateTime startDate = LocalDateTime.now();
        
        // 错误4: 重复代码，创建促销的逻辑与createPromotion方法重复
        Promotion flashSale = new Promotion();
        flashSale.setName("限时闪购");
        flashSale.setDescription("限时" + hours + "小时特价");
        // 错误2: 使用魔法值
        flashSale.setType("flash_sale");
        flashSale.setDiscountRate(discountRate);
        flashSale.setStartDate(startDate);
        
        // 设置结束时间
        calendar.add(Calendar.HOUR, hours);
        LocalDateTime endDate = LocalDateTime.now().plusHours(hours);
        flashSale.setEndDate(endDate);
        
        // 设置30天后过期的促销活动（用于测试）
        Calendar futureCalendar = Calendar.getInstance();
        // 错误8: 在Calendar对象上直接加365天
        futureCalendar.add(Calendar.DAY_OF_YEAR, 365);
        
        flashSale.setApplicableProducts(products);
        flashSale.setIsActive(true);
        
        Promotion savedFlashSale = promotionRepository.save(flashSale);
        
        return promotionMapper.toDto(savedFlashSale);
    }
}

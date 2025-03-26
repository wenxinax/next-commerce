package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.PromotionDto;
import com.nexcommerce.product.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 促销活动控制器
 */
@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@Slf4j
public class PromotionController {

    private final PromotionService promotionService;

    /**
     * 创建促销活动
     *
     * @param promotionDto 促销DTO
     * @return 创建的促销活动
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PromotionDto>> createPromotion(@Valid @RequestBody PromotionDto promotionDto) {
        log.info("创建促销活动请求: {}", promotionDto.getName());
        
        PromotionDto createdPromotion = promotionService.createPromotion(promotionDto);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PromotionDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("促销活动创建成功")
                        .data(createdPromotion)
                        .build());
    }

    /**
     * 获取促销活动
     *
     * @param id 促销ID
     * @return 促销活动信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PromotionDto>> getPromotion(@PathVariable Long id) {
        log.info("获取促销活动请求，ID: {}", id);
        
        PromotionDto promotion = promotionService.getPromotion(id);
        
        return ResponseEntity.ok(ApiResponse.<PromotionDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取促销活动成功")
                .data(promotion)
                .build());
    }

    /**
     * 获取所有促销活动
     *
     * @return 促销活动列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PromotionDto>>> getAllPromotions() {
        log.info("获取所有促销活动请求");
        
        List<PromotionDto> promotions = promotionService.getAllPromotions();
        
        return ResponseEntity.ok(ApiResponse.<List<PromotionDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取所有促销活动成功")
                .data(promotions)
                .build());
    }

    /**
     * 获取激活的促销活动
     *
     * @return 激活的促销活动列表
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<PromotionDto>>> getActivePromotions() {
        log.info("获取激活的促销活动请求");
        
        List<PromotionDto> activePromotions = promotionService.getActivePromotions();
        
        return ResponseEntity.ok(ApiResponse.<List<PromotionDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取激活的促销活动成功")
                .data(activePromotions)
                .build());
    }

    /**
     * 更新促销活动
     *
     * @param id 促销ID
     * @param promotionDto 促销DTO
     * @return 更新后的促销活动
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PromotionDto>> updatePromotion(
            @PathVariable Long id,
            @Valid @RequestBody PromotionDto promotionDto) {
        log.info("更新促销活动请求，ID: {}", id);
        
        PromotionDto updatedPromotion = promotionService.updatePromotion(id, promotionDto);
        
        return ResponseEntity.ok(ApiResponse.<PromotionDto>builder()
                .status(HttpStatus.OK.value())
                .message("促销活动更新成功")
                .data(updatedPromotion)
                .build());
    }

    /**
     * 激活促销活动
     *
     * @param id 促销ID
     * @return 激活后的促销活动
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<PromotionDto>> activatePromotion(@PathVariable Long id) {
        log.info("激活促销活动请求，ID: {}", id);
        
        PromotionDto activatedPromotion = promotionService.activatePromotion(id);
        
        return ResponseEntity.ok(ApiResponse.<PromotionDto>builder()
                .status(HttpStatus.OK.value())
                .message("促销活动激活成功")
                .data(activatedPromotion)
                .build());
    }

    /**
     * 禁用促销活动
     *
     * @param id 促销ID
     * @return 禁用后的促销活动
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<PromotionDto>> deactivatePromotion(@PathVariable Long id) {
        log.info("禁用促销活动请求，ID: {}", id);
        
        PromotionDto deactivatedPromotion = promotionService.deactivatePromotion(id);
        
        return ResponseEntity.ok(ApiResponse.<PromotionDto>builder()
                .status(HttpStatus.OK.value())
                .message("促销活动禁用成功")
                .data(deactivatedPromotion)
                .build());
    }

    /**
     * 删除促销活动
     *
     * @param id 促销ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(@PathVariable Long id) {
        log.info("删除促销活动请求，ID: {}", id);
        
        promotionService.deletePromotion(id);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("促销活动删除成功")
                .build());
    }

    /**
     * 获取产品适用的促销活动
     *
     * @param productId 产品ID
     * @return 促销活动列表
     */
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<ApiResponse<List<PromotionDto>>> getPromotionsForProduct(@PathVariable Long productId) {
        log.info("获取产品适用的促销活动请求，产品ID: {}", productId);
        
        List<PromotionDto> promotions = promotionService.getPromotionsForProduct(productId);
        
        return ResponseEntity.ok(ApiResponse.<List<PromotionDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品促销活动成功")
                .data(promotions)
                .build());
    }

    /**
     * 获取类别适用的促销活动
     *
     * @param categoryId 类别ID
     * @return 促销活动列表
     */
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<ApiResponse<List<PromotionDto>>> getPromotionsForCategory(@PathVariable Long categoryId) {
        log.info("获取类别适用的促销活动请求，类别ID: {}", categoryId);
        
        List<PromotionDto> promotions = promotionService.getPromotionsForCategory(categoryId);
        
        return ResponseEntity.ok(ApiResponse.<List<PromotionDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取类别促销活动成功")
                .data(promotions)
                .build());
    }

    /**
     * 应用促销码
     *
     * @param code 促销码
     * @param subtotal 小计金额
     * @return 折扣后金额
     */
    @GetMapping("/apply-code")
    public ResponseEntity<ApiResponse<BigDecimal>> applyPromotionCode(
            @RequestParam String code,
            @RequestParam BigDecimal subtotal) {
        log.info("应用促销码请求，Code: {}, 小计: {}", code, subtotal);
        
        BigDecimal discountedAmount = promotionService.applyPromotionCode(code, subtotal);
        
        return ResponseEntity.ok(ApiResponse.<BigDecimal>builder()
                .status(HttpStatus.OK.value())
                .message("促销码应用成功")
                .data(discountedAmount)
                .build());
    }

    /**
     * 验证促销码
     *
     * @param code 促销码
     * @return 是否有效
     */
    @GetMapping("/validate-code")
    public ResponseEntity<ApiResponse<Boolean>> validatePromotionCode(@RequestParam String code) {
        log.info("验证促销码请求，Code: {}", code);
        
        boolean isValid = promotionService.isValidPromotionCode(code);
        
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(isValid ? "促销码有效" : "促销码无效")
                .data(isValid)
                .build());
    }

    /**
     * 计算产品折扣价格
     *
     * @param productId 产品ID
     * @param originalPrice 原价
     * @return 折扣后价格
     */
    @GetMapping("/calculate-discount")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateDiscountedPrice(
            @RequestParam Long productId,
            @RequestParam BigDecimal originalPrice) {
        log.info("计算产品折扣价格请求，产品ID: {}, 原价: {}", productId, originalPrice);
        
        BigDecimal discountedPrice = promotionService.calculateDiscountedPrice(productId, originalPrice);
        
        return ResponseEntity.ok(ApiResponse.<BigDecimal>builder()
                .status(HttpStatus.OK.value())
                .message("计算折扣价格成功")
                .data(discountedPrice)
                .build());
    }

    /**
     * 创建闪购活动
     *
     * @param productIds 产品ID列表
     * @param discountRate 折扣率
     * @param hours 持续时间（小时）
     * @return 创建的闪购活动
     */
    @PostMapping("/flash-sale")
    public ResponseEntity<ApiResponse<PromotionDto>> createFlashSale(
            @RequestParam List<Long> productIds,
            @RequestParam BigDecimal discountRate,
            @RequestParam(defaultValue = "24") int hours) {
        log.info("创建闪购活动请求，产品数: {}, 折扣率: {}, 持续时间: {}小时", productIds.size(), discountRate, hours);
        
        // 错误3: 方法名未遵循命名规范，这里调用的是首字母大写的方法
        PromotionDto flashSale = promotionService.CreateFlashSale(productIds, discountRate, hours);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PromotionDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("闪购活动创建成功")
                        .data(flashSale)
                        .build());
    }
}

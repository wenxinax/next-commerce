package com.nexcommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 促销活动数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private BigDecimal discountRate;
    private BigDecimal discountAmount;
    private BigDecimal minPurchaseAmount;
    private BigDecimal maxDiscountAmount;
    private String code;
    private List<Long> applicableProductIds = new ArrayList<>();
    private List<Long> applicableCategoryIds = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Integer maxUsageCount;
    private Integer currentUsageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

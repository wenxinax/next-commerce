package com.nexcommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 产品属性数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDto {

    private Long id;
    private Long productId;

    @NotBlank(message = "属性名称不能为空")
    private String attributeName;

    @NotBlank(message = "属性值不能为空")
    private String attributeValue;
    
    private String attributeGroup;
    private Boolean isFilterable;
    private Boolean isVisible;
    private Integer sortOrder;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

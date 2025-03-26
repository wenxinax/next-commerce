package com.nexcommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 品牌数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {

    private Long id;

    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @NotBlank(message = "品牌标识不能为空")
    private String slug;

    private String description;
    private String logoUrl;
    private String websiteUrl;
    
    private Boolean isActive;
    private Integer sortOrder;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

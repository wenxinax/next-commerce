package com.nexcommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 产品图片数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {

    private Long id;
    private Long productId;

    @NotBlank(message = "图片URL不能为空")
    private String imageUrl;
    
    private String altText;
    private Boolean isPrimary;
    private Integer sortOrder;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

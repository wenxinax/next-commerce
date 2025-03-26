package com.nexcommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 类别数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "类别名称不能为空")
    private String name;

    @NotBlank(message = "类别标识不能为空")
    private String slug;

    private String description;
    private String imageUrl;
    
    private Long parentId;
    private String parentName;
    
    private Set<CategoryDto> children = new HashSet<>();
    
    private Boolean isActive;
    private Integer sortOrder;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

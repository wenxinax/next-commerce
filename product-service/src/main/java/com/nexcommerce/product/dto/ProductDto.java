package com.nexcommerce.product.dto;

import com.nexcommerce.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 产品数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "产品名称不能为空")
    private String name;

    @NotBlank(message = "产品SKU不能为空")
    private String sku;

    private String description;

    @NotNull(message = "产品价格不能为空")
    @Positive(message = "产品价格必须为正数")
    private BigDecimal price;

    private BigDecimal salePrice;

    @NotNull(message = "产品库存不能为空")
    @PositiveOrZero(message = "产品库存不能为负数")
    private Integer quantity;

    private Long categoryId;
    private String categoryName;

    private Long brandId;
    private String brandName;

    @NotNull(message = "产品状态不能为空")
    private Product.ProductStatus status;

    private Set<ProductImageDto> images = new HashSet<>();

    private Set<ProductAttributeDto> attributes = new HashSet<>();

    private Boolean isFeatured;

    private Double weight;
    private Double length;
    private Double width;
    private Double height;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 添加产品图片DTO
     *
     * @param imageDto 产品图片DTO
     */
    public void addImage(ProductImageDto imageDto) {
        this.images.add(imageDto);
    }

    /**
     * 添加产品属性DTO
     *
     * @param attributeDto 产品属性DTO
     */
    public void addAttribute(ProductAttributeDto attributeDto) {
        this.attributes.add(attributeDto);
    }
}

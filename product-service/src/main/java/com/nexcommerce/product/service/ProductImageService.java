package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.ProductImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 产品图片服务接口
 */
public interface ProductImageService {

    /**
     * 上传产品图片
     *
     * @param productId 产品ID
     * @param imageFile 图片文件
     * @param isPrimary 是否为主图
     * @return 上传后的图片DTO
     */
    ProductImageDto uploadProductImage(Long productId, MultipartFile imageFile, Boolean isPrimary);

    /**
     * 获取产品图片
     *
     * @param id 图片ID
     * @return 图片DTO
     */
    ProductImageDto getProductImage(Long id);

    /**
     * 获取产品所有图片
     *
     * @param productId 产品ID
     * @return 图片DTO列表
     */
    List<ProductImageDto> getProductImages(Long productId);

    /**
     * 获取产品主图
     *
     * @param productId 产品ID
     * @return 图片DTO
     */
    ProductImageDto getProductPrimaryImage(Long productId);

    /**
     * 更新产品图片
     *
     * @param id 图片ID
     * @param isPrimary 是否为主图
     * @param sortOrder 排序顺序
     * @return 更新后的图片DTO
     */
    ProductImageDto updateProductImage(Long id, Boolean isPrimary, Integer sortOrder);

    /**
     * 设置主图
     *
     * @param imageId 图片ID
     * @return 更新后的图片DTO
     */
    ProductImageDto setAsPrimaryImage(Long imageId);

    /**
     * 删除产品图片
     *
     * @param id 图片ID
     */
    void deleteProductImage(Long id);

    /**
     * 删除产品所有图片
     *
     * @param productId 产品ID
     */
    void deleteAllProductImages(Long productId);
}

package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.ProductAttributeDto;

import java.util.List;
import java.util.Map;

/**
 * 产品属性服务接口
 */
public interface ProductAttributeService {

    /**
     * 创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDto 属性DTO
     * @return 创建后的属性DTO
     */
    ProductAttributeDto createProductAttribute(Long productId, ProductAttributeDto attributeDto);

    /**
     * 批量创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDtos 属性DTO列表
     * @return 创建后的属性DTO列表
     */
    List<ProductAttributeDto> createProductAttributes(Long productId, List<ProductAttributeDto> attributeDtos);

    /**
     * 获取产品属性
     *
     * @param id 属性ID
     * @return 属性DTO
     */
    ProductAttributeDto getProductAttribute(Long id);

    /**
     * 获取产品所有属性
     *
     * @param productId 产品ID
     * @return 属性DTO列表
     */
    List<ProductAttributeDto> getProductAttributes(Long productId);

    /**
     * 获取产品属性（以Map形式返回）
     *
     * @param productId 产品ID
     * @return 属性名-属性值Map
     */
    Map<String, String> getProductAttributesAsMap(Long productId);

    /**
     * 更新产品属性
     *
     * @param id 属性ID
     * @param attributeDto 属性DTO
     * @return 更新后的属性DTO
     */
    ProductAttributeDto updateProductAttribute(Long id, ProductAttributeDto attributeDto);

    /**
     * 更新产品属性值
     *
     * @param id 属性ID
     * @param value 属性值
     * @return 更新后的属性DTO
     */
    ProductAttributeDto updateProductAttributeValue(Long id, String value);

    /**
     * 删除产品属性
     *
     * @param id 属性ID
     */
    void deleteProductAttribute(Long id);

    /**
     * 删除产品所有属性
     *
     * @param productId 产品ID
     */
    void deleteAllProductAttributes(Long productId);

    /**
     * 根据属性名搜索产品
     *
     * @param attributeName 属性名
     * @param attributeValue 属性值
     * @return 产品ID列表
     */
    List<Long> findProductIdsByAttribute(String attributeName, String attributeValue);

    /**
     * 获取指定属性名的所有可能值
     *
     * @param attributeName 属性名
     * @return 属性值列表
     */
    List<String> getAttributeValues(String attributeName);
}

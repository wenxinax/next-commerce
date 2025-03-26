package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.BrandDto;

import java.util.List;

/**
 * 品牌服务接口
 */
public interface BrandService {

    /**
     * 创建新品牌
     *
     * @param brandDto 品牌DTO
     * @return 创建后的品牌DTO
     */
    BrandDto createBrand(BrandDto brandDto);

    /**
     * 根据ID获取品牌
     *
     * @param id 品牌ID
     * @return 品牌DTO
     */
    BrandDto getBrandById(Long id);

    /**
     * 根据slug获取品牌
     *
     * @param slug 品牌标识符
     * @return 品牌DTO
     */
    BrandDto getBrandBySlug(String slug);

    /**
     * 获取所有品牌
     *
     * @return 品牌DTO列表
     */
    List<BrandDto> getAllBrands();

    /**
     * 获取所有激活的品牌
     *
     * @return 品牌DTO列表
     */
    List<BrandDto> getAllActiveBrands();

    /**
     * 更新品牌
     *
     * @param id 品牌ID
     * @param brandDto 品牌DTO
     * @return 更新后的品牌DTO
     */
    BrandDto updateBrand(Long id, BrandDto brandDto);

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     */
    void deleteBrand(Long id);

    /**
     * 按名称搜索品牌
     *
     * @param keyword 关键词
     * @return 品牌DTO列表
     */
    List<BrandDto> searchBrands(String keyword);
}

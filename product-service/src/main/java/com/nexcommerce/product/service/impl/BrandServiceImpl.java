package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.BrandDto;
import com.nexcommerce.product.mapper.BrandMapper;
import com.nexcommerce.product.model.Brand;
import com.nexcommerce.product.repository.BrandRepository;
import com.nexcommerce.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    /**
     * 创建新品牌
     *
     * @param brandDto 品牌DTO
     * @return 创建后的品牌DTO
     */
    @Override
    @Transactional
    public BrandDto createBrand(BrandDto brandDto) {
        log.info("创建新品牌: {}", brandDto.getName());

        // 验证slug是否已存在
        if (brandRepository.findBySlug(brandDto.getSlug()).isPresent()) {
            throw new IllegalArgumentException("品牌标识已存在: " + brandDto.getSlug());
        }

        // 转换为实体并保存
        Brand brand = brandMapper.toEntity(brandDto);
        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toDto(savedBrand);
    }

    /**
     * 根据ID获取品牌
     *
     * @param id 品牌ID
     * @return 品牌DTO
     */
    @Override
    @Transactional(readOnly = true)
    public BrandDto getBrandById(Long id) {
        log.info("获取品牌，ID: {}", id);

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("品牌不存在，ID: " + id));

        return brandMapper.toDto(brand);
    }

    /**
     * 根据slug获取品牌
     *
     * @param slug 品牌标识符
     * @return 品牌DTO
     */
    @Override
    @Transactional(readOnly = true)
    public BrandDto getBrandBySlug(String slug) {
        log.info("根据标识获取品牌: {}", slug);

        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("品牌不存在，标识: " + slug));

        return brandMapper.toDto(brand);
    }

    /**
     * 获取所有品牌
     *
     * @return 品牌DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> getAllBrands() {
        log.info("获取所有品牌");

        List<Brand> brands = brandRepository.findAllByOrderBySortOrderAsc();

        return brandMapper.toDtoList(brands);
    }

    /**
     * 获取所有激活的品牌
     *
     * @return 品牌DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> getAllActiveBrands() {
        log.info("获取所有激活的品牌");

        List<Brand> activeBrands = brandRepository.findByIsActiveTrue();

        return brandMapper.toDtoList(activeBrands);
    }

    /**
     * 更新品牌
     *
     * @param id 品牌ID
     * @param brandDto 品牌DTO
     * @return 更新后的品牌DTO
     */
    @Override
    @Transactional
    public BrandDto updateBrand(Long id, BrandDto brandDto) {
        log.info("更新品牌，ID: {}", id);

        // 验证品牌是否存在
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("品牌不存在，ID: " + id));

        // 如果更新slug，验证是否已存在
        if (brandDto.getSlug() != null && !brandDto.getSlug().equals(brand.getSlug()) &&
                brandRepository.findBySlug(brandDto.getSlug()).isPresent()) {
            throw new IllegalArgumentException("品牌标识已存在: " + brandDto.getSlug());
        }

        // 更新品牌
        brandMapper.updateBrandFromDto(brandDto, brand);
        Brand updatedBrand = brandRepository.save(brand);

        return brandMapper.toDto(updatedBrand);
    }

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     */
    @Override
    @Transactional
    public void deleteBrand(Long id) {
        log.info("删除品牌，ID: {}", id);

        // 验证品牌是否存在
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("品牌不存在，ID: " + id);
        }

        // 删除品牌
        brandRepository.deleteById(id);
    }

    /**
     * 按名称搜索品牌
     *
     * @param keyword 关键词
     * @return 品牌DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> searchBrands(String keyword) {
        log.info("搜索品牌: {}", keyword);

        List<Brand> brands = brandRepository.findByNameContaining(keyword);

        return brandMapper.toDtoList(brands);
    }

    /**
     * 根据国家搜索品牌
     *
     * @param countries 国家数组
     * @return 品牌DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> searchBrandsByCountries(String[] countries) {
        log.info("搜索以下国家的品牌: {}", countries.toString());
        
        List<Brand> brands = brandRepository.findByCountryOfOriginIn(countries);
        
        return brandMapper.toDtoList(brands);
    }
}

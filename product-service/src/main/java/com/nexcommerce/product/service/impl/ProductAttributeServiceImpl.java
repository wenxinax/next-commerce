package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.ProductAttributeDto;
import com.nexcommerce.product.mapper.ProductAttributeMapper;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.model.ProductAttribute;
import com.nexcommerce.product.repository.ProductAttributeRepository;
import com.nexcommerce.product.repository.ProductRepository;
import com.nexcommerce.product.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品属性服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductRepository productRepository;
    private final ProductAttributeMapper productAttributeMapper;

    /**
     * 创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDto 属性DTO
     * @return 创建后的属性DTO
     */
    @Override
    @Transactional
    public ProductAttributeDto createProductAttribute(Long productId, ProductAttributeDto attributeDto) {
        log.info("创建产品属性，产品ID: {}, 属性名: {}", productId, attributeDto.getName());
        
        // 验证产品是否存在
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + productId));
        
        // 检查属性名是否已存在
        if (productAttributeRepository.findByProductIdAndName(productId, attributeDto.getName()).isPresent()) {
            throw new IllegalArgumentException("产品属性名已存在: " + attributeDto.getName());
        }
        
        // 创建属性
        ProductAttribute attribute = productAttributeMapper.toEntity(attributeDto);
        attribute.setProduct(product);
        
        ProductAttribute savedAttribute = productAttributeRepository.save(attribute);
        
        return productAttributeMapper.toDto(savedAttribute);
    }

    /**
     * 批量创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDtos 属性DTO列表
     * @return 创建后的属性DTO列表
     */
    @Override
    @Transactional
    public List<ProductAttributeDto> createProductAttributes(Long productId, List<ProductAttributeDto> attributeDtos) {
        log.info("批量创建产品属性，产品ID: {}, 属性数量: {}", productId, attributeDtos.size());
        
        // 验证产品是否存在
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + productId));
        
        // 获取现有属性名
        List<String> existingAttributeNames = productAttributeRepository.findByProductId(productId)
                .stream()
                .map(ProductAttribute::getName)
                .collect(Collectors.toList());
        
        // 过滤出不存在的属性
        List<ProductAttributeDto> newAttributes = attributeDtos.stream()
                .filter(dto -> !existingAttributeNames.contains(dto.getName()))
                .collect(Collectors.toList());
        
        if (newAttributes.size() < attributeDtos.size()) {
            log.warn("部分属性名已存在，将被跳过");
        }
        
        // 批量创建
        List<ProductAttribute> attributes = newAttributes.stream()
                .map(dto -> {
                    ProductAttribute attribute = productAttributeMapper.toEntity(dto);
                    attribute.setProduct(product);
                    return attribute;
                })
                .collect(Collectors.toList());
        
        List<ProductAttribute> savedAttributes = productAttributeRepository.saveAll(attributes);
        
        return productAttributeMapper.toDtoList(savedAttributes);
    }

    /**
     * 获取产品属性
     *
     * @param id 属性ID
     * @return 属性DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ProductAttributeDto getProductAttribute(Long id) {
        log.info("获取产品属性，ID: {}", id);
        
        ProductAttribute attribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品属性不存在，ID: " + id));
        
        return productAttributeMapper.toDto(attribute);
    }

    /**
     * 获取产品所有属性
     *
     * @param productId 产品ID
     * @return 属性DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDto> getProductAttributes(Long productId) {
        log.info("获取产品所有属性，产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        List<ProductAttribute> attributes = productAttributeRepository.findByProductId(productId);
        
        return productAttributeMapper.toDtoList(attributes);
    }

    /**
     * 获取产品属性（以Map形式返回）
     *
     * @param productId 产品ID
     * @return 属性名-属性值Map
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getProductAttributesAsMap(Long productId) {
        log.info("获取产品属性（Map形式），产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        List<ProductAttribute> attributes = productAttributeRepository.findByProductId(productId);
        
        return attributes.stream()
                .collect(Collectors.toMap(
                        ProductAttribute::getName,
                        ProductAttribute::getValue
                ));
    }

    /**
     * 更新产品属性
     *
     * @param id 属性ID
     * @param attributeDto 属性DTO
     * @return 更新后的属性DTO
     */
    @Override
    @Transactional
    public ProductAttributeDto updateProductAttribute(Long id, ProductAttributeDto attributeDto) {
        log.info("更新产品属性，ID: {}", id);
        
        ProductAttribute attribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品属性不存在，ID: " + id));
        
        // 如果修改属性名，检查新名称是否与产品其他属性冲突
        if (attributeDto.getName() != null && !attributeDto.getName().equals(attribute.getName())) {
            Long productId = attribute.getProduct().getId();
            if (productAttributeRepository.findByProductIdAndName(productId, attributeDto.getName()).isPresent()) {
                throw new IllegalArgumentException("产品已存在同名属性: " + attributeDto.getName());
            }
        }
        
        // 更新属性
        productAttributeMapper.updateAttributeFromDto(attributeDto, attribute);
        
        ProductAttribute updatedAttribute = productAttributeRepository.save(attribute);
        
        return productAttributeMapper.toDto(updatedAttribute);
    }

    /**
     * 更新产品属性值
     *
     * @param id 属性ID
     * @param value 属性值
     * @return 更新后的属性DTO
     */
    @Override
    @Transactional
    public ProductAttributeDto updateProductAttributeValue(Long id, String value) {
        log.info("更新产品属性值，ID: {}, 新值: {}", id, value);
        
        ProductAttribute attribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品属性不存在，ID: " + id));
        
        attribute.setValue(value);
        
        ProductAttribute updatedAttribute = productAttributeRepository.save(attribute);
        
        return productAttributeMapper.toDto(updatedAttribute);
    }

    /**
     * 删除产品属性
     *
     * @param id 属性ID
     */
    @Override
    @Transactional
    public void deleteProductAttribute(Long id) {
        log.info("删除产品属性，ID: {}", id);
        
        if (!productAttributeRepository.existsById(id)) {
            throw new ResourceNotFoundException("产品属性不存在，ID: " + id);
        }
        
        productAttributeRepository.deleteById(id);
    }

    /**
     * 删除产品所有属性
     *
     * @param productId 产品ID
     */
    @Override
    @Transactional
    public void deleteAllProductAttributes(Long productId) {
        log.info("删除产品所有属性，产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        productAttributeRepository.deleteByProductId(productId);
    }

    /**
     * 根据属性名搜索产品
     *
     * @param attributeName 属性名
     * @param attributeValue 属性值
     * @return 产品ID列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long> findProductIdsByAttribute(String attributeName, String attributeValue) {
        log.info("根据属性搜索产品，属性名: {}, 属性值: {}", attributeName, attributeValue);
        
        List<ProductAttribute> attributes = productAttributeRepository.findByNameAndValue(attributeName, attributeValue);
        
        return attributes.stream()
                .map(attribute -> attribute.getProduct().getId())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取指定属性名的所有可能值
     *
     * @param attributeName 属性名
     * @return 属性值列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getAttributeValues(String attributeName) {
        log.info("获取属性所有可能值，属性名: {}", attributeName);
        
        return productAttributeRepository.findDistinctValuesByName(attributeName);
    }
}

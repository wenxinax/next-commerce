package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.ProductDto;
import com.nexcommerce.product.mapper.ProductMapper;
import com.nexcommerce.product.model.Brand;
import com.nexcommerce.product.model.Category;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.repository.BrandRepository;
import com.nexcommerce.product.repository.CategoryRepository;
import com.nexcommerce.product.repository.ProductRepository;
import com.nexcommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    /**
     * 创建新产品
     *
     * @param productDto 产品DTO
     * @return 创建后的产品DTO
     */
    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("创建新产品: {}", productDto.getName());
        
        // 检查SKU是否已存在
        if (productRepository.findBySku(productDto.getSku()).isPresent()) {
            throw new IllegalArgumentException("SKU已存在: " + productDto.getSku());
        }
        
        // 转换DTO为实体
        Product product = productMapper.toEntity(productDto);
        
        // 设置类别
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("类别不存在，ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        
        // 设置品牌
        if (productDto.getBrandId() != null) {
            Brand brand = brandRepository.findById(productDto.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("品牌不存在，ID: " + productDto.getBrandId()));
            product.setBrand(brand);
        }
        
        // 保存产品
        Product savedProduct = productRepository.save(product);
        
        return productMapper.toDto(savedProduct);
    }

    /**
     * 根据ID获取产品
     *
     * @param id 产品ID
     * @return 产品DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        log.info("获取产品，ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + id));
        
        return productMapper.toDto(product);
    }

    /**
     * 根据SKU获取产品
     *
     * @param sku 产品SKU
     * @return 产品DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductBySku(String sku) {
        log.info("根据SKU获取产品: {}", sku);
        
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，SKU: " + sku));
        
        return productMapper.toDto(product);
    }

    /**
     * 获取所有产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        log.info("获取所有产品，页码: {}, 每页大小: {}", pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 更新产品
     *
     * @param id 产品ID
     * @param productDto 产品DTO
     * @return 更新后的产品DTO
     */
    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("更新产品，ID: {}", id);
        
        // 获取现有产品
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + id));
        
        // 如果修改了SKU，检查是否已存在
        if (productDto.getSku() != null && !productDto.getSku().equals(product.getSku()) &&
                productRepository.findBySku(productDto.getSku()).isPresent()) {
            throw new IllegalArgumentException("SKU已存在: " + productDto.getSku());
        }
        
        // 更新基本属性
        productMapper.updateProductFromDto(productDto, product);
        
        // 更新类别
        if (productDto.getCategoryId() != null && 
                (product.getCategory() == null || !productDto.getCategoryId().equals(product.getCategory().getId()))) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("类别不存在，ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        
        // 更新品牌
        if (productDto.getBrandId() != null && 
                (product.getBrand() == null || !productDto.getBrandId().equals(product.getBrand().getId()))) {
            Brand brand = brandRepository.findById(productDto.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("品牌不存在，ID: " + productDto.getBrandId()));
            product.setBrand(brand);
        }
        
        // 保存更新
        Product updatedProduct = productRepository.save(product);
        
        return productMapper.toDto(updatedProduct);
    }

    /**
     * 删除产品
     *
     * @param id 产品ID
     */
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("删除产品，ID: {}", id);
        
        // 检查产品是否存在
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + id);
        }
        
        productRepository.deleteById(id);
    }

    /**
     * 根据名称搜索产品（分页）
     *
     * @param name 产品名称（关键字）
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> searchProductsByName(String name, Pageable pageable) {
        log.info("按名称搜索产品: {}", name);
        
        Page<Product> productPage = productRepository.findByNameContaining(name, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 根据类别ID获取产品（分页）
     *
     * @param categoryId 类别ID
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        log.info("获取类别下的产品，类别ID: {}", categoryId);
        
        // 检查类别是否存在
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("类别不存在，ID: " + categoryId);
        }
        
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 根据品牌ID获取产品（分页）
     *
     * @param brandId 品牌ID
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByBrandId(Long brandId, Pageable pageable) {
        log.info("获取品牌下的产品，品牌ID: {}", brandId);
        
        // 检查品牌是否存在
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("品牌不存在，ID: " + brandId);
        }
        
        Page<Product> productPage = productRepository.findByBrandId(brandId, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 获取特价产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsOnSale(Pageable pageable) {
        log.info("获取特价产品");
        
        Page<Product> productPage = productRepository.findBySalePriceIsNotNull(pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 获取价格在指定范围内的产品（分页）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        log.info("按价格范围获取产品: {} - {}", minPrice, maxPrice);
        
        Page<Product> productPage = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 获取推荐产品（分页）
     *
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getFeaturedProducts(Pageable pageable) {
        log.info("获取推荐产品");
        
        Page<Product> productPage = productRepository.findByIsFeatured(true, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 根据状态获取产品（分页）
     *
     * @param status 产品状态
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByStatus(Product.ProductStatus status, Pageable pageable) {
        log.info("按状态获取产品: {}", status);
        
        Page<Product> productPage = productRepository.findByStatus(status, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 根据多个条件筛选产品（分页）
     *
     * @param categoryId 类别ID（可选）
     * @param brandId 品牌ID（可选）
     * @param minPrice 最低价格（可选）
     * @param maxPrice 最高价格（可选）
     * @param pageable 分页参数
     * @return 产品分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByFilters(Long categoryId, Long brandId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        log.info("按条件筛选产品: 类别ID={}, 品牌ID={}, 价格范围={}-{}", categoryId, brandId, minPrice, maxPrice);
        
        Page<Product> productPage = productRepository.findByFilters(categoryId, brandId, minPrice, maxPrice, pageable);
        
        return productPage.map(productMapper::toDto);
    }

    /**
     * 更新产品库存
     *
     * @param id 产品ID
     * @param quantityChange 库存变化（正数增加，负数减少）
     * @return 更新后的产品DTO
     */
    @Override
    @Transactional
    public ProductDto updateProductStock(Long id, Integer quantityChange) {
        log.info("更新产品库存，ID: {}, 变化量: {}", id, quantityChange);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + id));
        
        // 计算新库存
        int newQuantity = product.getQuantity() + quantityChange;
        
        // 验证新库存不能为负数
        if (newQuantity < 0) {
            throw new IllegalArgumentException("库存不足，当前库存: " + product.getQuantity() + ", 请求减少: " + Math.abs(quantityChange));
        }
        
        // 更新库存
        product.setQuantity(newQuantity);
        
        // 如果库存为0，更新产品状态
        if (newQuantity == 0) {
            product.setStatus(Product.ProductStatus.OUT_OF_STOCK);
        } else if (product.getStatus() == Product.ProductStatus.OUT_OF_STOCK) {
            product.setStatus(Product.ProductStatus.ACTIVE);
        }
        
        Product updatedProduct = productRepository.save(product);
        
        return productMapper.toDto(updatedProduct);
    }

    /**
     * 获取低库存产品
     *
     * @param threshold 库存阈值
     * @return 低库存产品列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getLowStockProducts(Integer threshold) {
        log.info("获取低库存产品，阈值: {}", threshold);
        
        List<Product> products = productRepository.findByQuantityLessThanAndStatusNot(threshold, Product.ProductStatus.DISCONTINUED);
        
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

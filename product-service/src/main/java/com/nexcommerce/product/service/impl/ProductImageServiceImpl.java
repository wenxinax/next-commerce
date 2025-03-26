package com.nexcommerce.product.service.impl;

import com.nexcommerce.common.exception.ResourceNotFoundException;
import com.nexcommerce.product.dto.ProductImageDto;
import com.nexcommerce.product.mapper.ProductImageMapper;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.model.ProductImage;
import com.nexcommerce.product.repository.ProductImageRepository;
import com.nexcommerce.product.repository.ProductRepository;
import com.nexcommerce.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 产品图片服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductImageMapper productImageMapper;
    
    // 配置上传目录，实际项目中应该通过配置文件设置
    private final String uploadDir = "uploads/products";

    /**
     * 上传产品图片
     *
     * @param productId 产品ID
     * @param imageFile 图片文件
     * @param isPrimary 是否为主图
     * @return 上传后的图片DTO
     */
    @Override
    @Transactional
    public ProductImageDto uploadProductImage(Long productId, MultipartFile imageFile, Boolean isPrimary) {
        log.info("上传产品图片，产品ID: {}, 是否为主图: {}", productId, isPrimary);
        
        // 验证产品是否存在
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("产品不存在，ID: " + productId));
        
        try {
            // 确保上传目录存在
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 创建唯一文件名
            String originalFilename = imageFile.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : ".jpg";
            
            String uniqueFilename = productId + "_" + 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + 
                    UUID.randomUUID().toString().substring(0, 8) + fileExtension;
            
            // 保存文件
            Path targetPath = uploadPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), targetPath);
            
            // 如果设置为主图，先将其他图片设置为非主图
            if (Boolean.TRUE.equals(isPrimary)) {
                productImageRepository.resetPrimaryImages(productId);
            }
            
            // 获取当前最大排序值
            Integer maxSortOrder = productImageRepository.findMaxSortOrderByProductId(productId);
            int sortOrder = maxSortOrder != null ? maxSortOrder + 1 : 0;
            
            // 创建图片记录
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImageUrl(targetPath.toString());
            productImage.setFileName(uniqueFilename);
            productImage.setOriginalFileName(originalFilename);
            productImage.setFileSize(imageFile.getSize());
            productImage.setMimeType(imageFile.getContentType());
            productImage.setIsPrimary(isPrimary);
            productImage.setSortOrder(sortOrder);
            productImage.setCreatedAt(LocalDateTime.now());
            
            ProductImage savedImage = productImageRepository.save(productImage);
            
            return productImageMapper.toDto(savedImage);
            
        } catch (IOException e) {
            log.error("上传产品图片失败", e);
            throw new RuntimeException("上传产品图片失败: " + e.getMessage());
        }
    }

    /**
     * 获取产品图片
     *
     * @param id 图片ID
     * @return 图片DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ProductImageDto getProductImage(Long id) {
        log.info("获取产品图片，ID: {}", id);
        
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品图片不存在，ID: " + id));
        
        return productImageMapper.toDto(productImage);
    }

    /**
     * 获取产品所有图片
     *
     * @param productId 产品ID
     * @return 图片DTO列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductImageDto> getProductImages(Long productId) {
        log.info("获取产品所有图片，产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        List<ProductImage> images = productImageRepository.findByProductIdOrderBySortOrderAsc(productId);
        
        return productImageMapper.toDtoList(images);
    }

    /**
     * 获取产品主图
     *
     * @param productId 产品ID
     * @return 图片DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ProductImageDto getProductPrimaryImage(Long productId) {
        log.info("获取产品主图，产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        ProductImage primaryImage = productImageRepository.findByProductIdAndIsPrimaryTrue(productId)
                .orElseThrow(() -> new ResourceNotFoundException("产品主图不存在，产品ID: " + productId));
        
        return productImageMapper.toDto(primaryImage);
    }

    /**
     * 更新产品图片
     *
     * @param id 图片ID
     * @param isPrimary 是否为主图
     * @param sortOrder 排序顺序
     * @return 更新后的图片DTO
     */
    @Override
    @Transactional
    public ProductImageDto updateProductImage(Long id, Boolean isPrimary, Integer sortOrder) {
        log.info("更新产品图片，ID: {}, 是否为主图: {}, 排序顺序: {}", id, isPrimary, sortOrder);
        
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品图片不存在，ID: " + id));
        
        // 如果设置为主图，先将该产品的其他图片设置为非主图
        if (Boolean.TRUE.equals(isPrimary) && !productImage.getIsPrimary()) {
            productImageRepository.resetPrimaryImages(productImage.getProduct().getId());
            productImage.setIsPrimary(true);
        } else if (isPrimary != null) {
            productImage.setIsPrimary(isPrimary);
        }
        
        // 更新排序顺序
        if (sortOrder != null) {
            productImage.setSortOrder(sortOrder);
        }
        
        productImage.setUpdatedAt(LocalDateTime.now());
        ProductImage updatedImage = productImageRepository.save(productImage);
        
        return productImageMapper.toDto(updatedImage);
    }

    /**
     * 设置主图
     *
     * @param imageId 图片ID
     * @return 更新后的图片DTO
     */
    @Override
    @Transactional
    public ProductImageDto setAsPrimaryImage(Long imageId) {
        log.info("设置主图，图片ID: {}", imageId);
        
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("产品图片不存在，ID: " + imageId));
        
        Long productId = productImage.getProduct().getId();
        
        // 重置该产品的所有图片为非主图
        productImageRepository.resetPrimaryImages(productId);
        
        // 设置当前图片为主图
        productImage.setIsPrimary(true);
        productImage.setUpdatedAt(LocalDateTime.now());
        
        ProductImage updatedImage = productImageRepository.save(productImage);
        
        return productImageMapper.toDto(updatedImage);
    }

    /**
     * 删除产品图片
     *
     * @param id 图片ID
     */
    @Override
    @Transactional
    public void deleteProductImage(Long id) {
        log.info("删除产品图片，ID: {}", id);
        
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("产品图片不存在，ID: " + id));
        
        // 删除物理文件
        try {
            Path filePath = Paths.get(productImage.getImageUrl());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            log.error("删除产品图片文件失败", e);
            // 继续删除数据库记录，即使文件删除失败
        }
        
        // 检查是否为主图
        boolean isPrimary = productImage.getIsPrimary();
        
        // 删除数据库记录
        productImageRepository.delete(productImage);
        
        // 如果删除的是主图，尝试将第一张图片设为主图
        if (isPrimary) {
            productImageRepository.findFirstByProductIdOrderBySortOrderAsc(productImage.getProduct().getId())
                    .ifPresent(firstImage -> {
                        firstImage.setIsPrimary(true);
                        productImageRepository.save(firstImage);
                    });
        }
    }

    /**
     * 删除产品所有图片
     *
     * @param productId 产品ID
     */
    @Override
    @Transactional
    public void deleteAllProductImages(Long productId) {
        log.info("删除产品所有图片，产品ID: {}", productId);
        
        // 验证产品是否存在
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("产品不存在，ID: " + productId);
        }
        
        List<ProductImage> images = productImageRepository.findByProductId(productId);
        
        // 删除所有物理文件
        for (ProductImage image : images) {
            try {
                Path filePath = Paths.get(image.getImageUrl());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                log.error("删除产品图片文件失败，图片ID: {}", image.getId(), e);
                // 继续处理其他图片
            }
        }
        
        // 删除所有数据库记录
        productImageRepository.deleteByProductId(productId);
    }
}

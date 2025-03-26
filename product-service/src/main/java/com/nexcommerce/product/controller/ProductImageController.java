package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.ProductImageDto;
import com.nexcommerce.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 产品图片控制器
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductImageController {

    private final ProductImageService productImageService;

    /**
     * 上传产品图片
     *
     * @param productId 产品ID
     * @param imageFile 图片文件
     * @param isPrimary 是否为主图
     * @return 上传后的图片信息
     */
    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductImageDto>> uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        log.info("上传产品图片请求，产品ID: {}, 是否为主图: {}", productId, isPrimary);
        
        ProductImageDto productImage = productImageService.uploadProductImage(productId, imageFile, isPrimary);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProductImageDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("图片上传成功")
                        .data(productImage)
                        .build());
    }

    /**
     * 获取产品图片
     *
     * @param imageId 图片ID
     * @return 图片信息
     */
    @GetMapping("/images/{imageId}")
    public ResponseEntity<ApiResponse<ProductImageDto>> getProductImage(@PathVariable Long imageId) {
        log.info("获取产品图片请求，图片ID: {}", imageId);
        
        ProductImageDto productImage = productImageService.getProductImage(imageId);
        
        return ResponseEntity.ok(ApiResponse.<ProductImageDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取图片成功")
                .data(productImage)
                .build());
    }

    /**
     * 获取产品所有图片
     *
     * @param productId 产品ID
     * @return 图片列表
     */
    @GetMapping("/{productId}/images")
    public ResponseEntity<ApiResponse<List<ProductImageDto>>> getProductImages(@PathVariable Long productId) {
        log.info("获取产品所有图片请求，产品ID: {}", productId);
        
        List<ProductImageDto> images = productImageService.getProductImages(productId);
        
        return ResponseEntity.ok(ApiResponse.<List<ProductImageDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品图片列表成功")
                .data(images)
                .build());
    }

    /**
     * 获取产品主图
     *
     * @param productId 产品ID
     * @return 主图信息
     */
    @GetMapping("/{productId}/images/primary")
    public ResponseEntity<ApiResponse<ProductImageDto>> getProductPrimaryImage(@PathVariable Long productId) {
        log.info("获取产品主图请求，产品ID: {}", productId);
        
        ProductImageDto primaryImage = productImageService.getProductPrimaryImage(productId);
        
        return ResponseEntity.ok(ApiResponse.<ProductImageDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品主图成功")
                .data(primaryImage)
                .build());
    }

    /**
     * 更新产品图片
     *
     * @param imageId 图片ID
     * @param isPrimary 是否为主图
     * @param sortOrder 排序顺序
     * @return 更新后的图片信息
     */
    @PutMapping("/images/{imageId}")
    public ResponseEntity<ApiResponse<ProductImageDto>> updateProductImage(
            @PathVariable Long imageId,
            @RequestParam(required = false) Boolean isPrimary,
            @RequestParam(required = false) Integer sortOrder) {
        log.info("更新产品图片请求，图片ID: {}, 是否为主图: {}, 排序顺序: {}", imageId, isPrimary, sortOrder);
        
        ProductImageDto updatedImage = productImageService.updateProductImage(imageId, isPrimary, sortOrder);
        
        return ResponseEntity.ok(ApiResponse.<ProductImageDto>builder()
                .status(HttpStatus.OK.value())
                .message("图片更新成功")
                .data(updatedImage)
                .build());
    }

    /**
     * 设置主图
     *
     * @param imageId 图片ID
     * @return 更新后的图片信息
     */
    @PutMapping("/images/{imageId}/primary")
    public ResponseEntity<ApiResponse<ProductImageDto>> setAsPrimaryImage(@PathVariable Long imageId) {
        log.info("设置主图请求，图片ID: {}", imageId);
        
        ProductImageDto updatedImage = productImageService.setAsPrimaryImage(imageId);
        
        return ResponseEntity.ok(ApiResponse.<ProductImageDto>builder()
                .status(HttpStatus.OK.value())
                .message("设置主图成功")
                .data(updatedImage)
                .build());
    }

    /**
     * 删除产品图片
     *
     * @param imageId 图片ID
     * @return 操作结果
     */
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(@PathVariable Long imageId) {
        log.info("删除产品图片请求，图片ID: {}", imageId);
        
        productImageService.deleteProductImage(imageId);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("图片删除成功")
                .build());
    }

    /**
     * 删除产品所有图片
     *
     * @param productId 产品ID
     * @return 操作结果
     */
    @DeleteMapping("/{productId}/images")
    public ResponseEntity<ApiResponse<Void>> deleteAllProductImages(@PathVariable Long productId) {
        log.info("删除产品所有图片请求，产品ID: {}", productId);
        
        productImageService.deleteAllProductImages(productId);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("所有图片删除成功")
                .build());
    }
}

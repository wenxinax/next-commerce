package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.ProductAttributeDto;
import com.nexcommerce.product.service.ProductAttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 产品属性控制器
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    /**
     * 创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDto 属性DTO
     * @return 创建的属性
     */
    @PostMapping("/{productId}/attributes")
    public ResponseEntity<ApiResponse<ProductAttributeDto>> createProductAttribute(
            @PathVariable Long productId,
            @Valid @RequestBody ProductAttributeDto attributeDto) {
        log.info("创建产品属性请求，产品ID: {}, 属性名: {}", productId, attributeDto.getName());
        
        ProductAttributeDto createdAttribute = productAttributeService.createProductAttribute(productId, attributeDto);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProductAttributeDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("属性创建成功")
                        .data(createdAttribute)
                        .build());
    }

    /**
     * 批量创建产品属性
     *
     * @param productId 产品ID
     * @param attributeDtos 属性DTO列表
     * @return 创建的属性列表
     */
    @PostMapping("/{productId}/attributes/batch")
    public ResponseEntity<ApiResponse<List<ProductAttributeDto>>> createProductAttributes(
            @PathVariable Long productId,
            @Valid @RequestBody List<ProductAttributeDto> attributeDtos) {
        log.info("批量创建产品属性请求，产品ID: {}, 属性数量: {}", productId, attributeDtos.size());
        
        List<ProductAttributeDto> createdAttributes = productAttributeService.createProductAttributes(productId, attributeDtos);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<List<ProductAttributeDto>>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("属性批量创建成功")
                        .data(createdAttributes)
                        .build());
    }

    /**
     * 获取产品属性
     *
     * @param attributeId 属性ID
     * @return 属性信息
     */
    @GetMapping("/attributes/{attributeId}")
    public ResponseEntity<ApiResponse<ProductAttributeDto>> getProductAttribute(@PathVariable Long attributeId) {
        log.info("获取产品属性请求，属性ID: {}", attributeId);
        
        ProductAttributeDto attribute = productAttributeService.getProductAttribute(attributeId);
        
        return ResponseEntity.ok(ApiResponse.<ProductAttributeDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取属性成功")
                .data(attribute)
                .build());
    }

    /**
     * 获取产品所有属性
     *
     * @param productId 产品ID
     * @return 属性列表
     */
    @GetMapping("/{productId}/attributes")
    public ResponseEntity<ApiResponse<List<ProductAttributeDto>>> getProductAttributes(@PathVariable Long productId) {
        log.info("获取产品所有属性请求，产品ID: {}", productId);
        
        List<ProductAttributeDto> attributes = productAttributeService.getProductAttributes(productId);
        
        return ResponseEntity.ok(ApiResponse.<List<ProductAttributeDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品属性列表成功")
                .data(attributes)
                .build());
    }

    /**
     * 获取产品属性（以Map形式）
     *
     * @param productId 产品ID
     * @return 属性Map
     */
    @GetMapping("/{productId}/attributes/map")
    public ResponseEntity<ApiResponse<Map<String, String>>> getProductAttributesAsMap(@PathVariable Long productId) {
        log.info("获取产品属性（Map形式）请求，产品ID: {}", productId);
        
        Map<String, String> attributesMap = productAttributeService.getProductAttributesAsMap(productId);
        
        return ResponseEntity.ok(ApiResponse.<Map<String, String>>builder()
                .status(HttpStatus.OK.value())
                .message("获取产品属性成功")
                .data(attributesMap)
                .build());
    }

    /**
     * 更新产品属性
     *
     * @param attributeId 属性ID
     * @param attributeDto 属性DTO
     * @return 更新后的属性
     */
    @PutMapping("/attributes/{attributeId}")
    public ResponseEntity<ApiResponse<ProductAttributeDto>> updateProductAttribute(
            @PathVariable Long attributeId,
            @Valid @RequestBody ProductAttributeDto attributeDto) {
        log.info("更新产品属性请求，属性ID: {}", attributeId);
        
        ProductAttributeDto updatedAttribute = productAttributeService.updateProductAttribute(attributeId, attributeDto);
        
        return ResponseEntity.ok(ApiResponse.<ProductAttributeDto>builder()
                .status(HttpStatus.OK.value())
                .message("属性更新成功")
                .data(updatedAttribute)
                .build());
    }

    /**
     * 更新产品属性值
     *
     * @param attributeId 属性ID
     * @param value 属性值
     * @return 更新后的属性
     */
    @PatchMapping("/attributes/{attributeId}")
    public ResponseEntity<ApiResponse<ProductAttributeDto>> updateProductAttributeValue(
            @PathVariable Long attributeId,
            @RequestParam String value) {
        log.info("更新产品属性值请求，属性ID: {}, 新值: {}", attributeId, value);
        
        ProductAttributeDto updatedAttribute = productAttributeService.updateProductAttributeValue(attributeId, value);
        
        return ResponseEntity.ok(ApiResponse.<ProductAttributeDto>builder()
                .status(HttpStatus.OK.value())
                .message("属性值更新成功")
                .data(updatedAttribute)
                .build());
    }

    /**
     * 删除产品属性
     *
     * @param attributeId 属性ID
     * @return 操作结果
     */
    @DeleteMapping("/attributes/{attributeId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductAttribute(@PathVariable Long attributeId) {
        log.info("删除产品属性请求，属性ID: {}", attributeId);
        
        productAttributeService.deleteProductAttribute(attributeId);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("属性删除成功")
                .build());
    }

    /**
     * 删除产品所有属性
     *
     * @param productId 产品ID
     * @return 操作结果
     */
    @DeleteMapping("/{productId}/attributes")
    public ResponseEntity<ApiResponse<Void>> deleteAllProductAttributes(@PathVariable Long productId) {
        log.info("删除产品所有属性请求，产品ID: {}", productId);
        
        productAttributeService.deleteAllProductAttributes(productId);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("所有属性删除成功")
                .build());
    }

    /**
     * 根据属性搜索产品
     *
     * @param attributeName 属性名
     * @param attributeValue 属性值
     * @return 产品ID列表
     */
    @GetMapping("/search/by-attribute")
    public ResponseEntity<ApiResponse<List<Long>>> findProductsByAttribute(
            @RequestParam String attributeName,
            @RequestParam String attributeValue) {
        log.info("根据属性搜索产品请求，属性名: {}, 属性值: {}", attributeName, attributeValue);
        
        List<Long> productIds = productAttributeService.findProductIdsByAttribute(attributeName, attributeValue);
        
        return ResponseEntity.ok(ApiResponse.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message("产品搜索成功")
                .data(productIds)
                .build());
    }

    /**
     * 获取属性所有可能值
     *
     * @param attributeName 属性名
     * @return 属性值列表
     */
    @GetMapping("/attributes/values")
    public ResponseEntity<ApiResponse<List<String>>> getAttributeValues(@RequestParam String attributeName) {
        log.info("获取属性所有可能值请求，属性名: {}", attributeName);
        
        List<String> attributeValues = productAttributeService.getAttributeValues(attributeName);
        
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .status(HttpStatus.OK.value())
                .message("获取属性值成功")
                .data(attributeValues)
                .build());
    }
}

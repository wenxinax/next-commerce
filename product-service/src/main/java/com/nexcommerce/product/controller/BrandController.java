package com.nexcommerce.product.controller;

import com.nexcommerce.common.dto.ApiResponse;
import com.nexcommerce.product.dto.BrandDto;
import com.nexcommerce.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌控制器
 */
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {

    private final BrandService brandService;

    /**
     * 创建新品牌
     *
     * @param brandDto 品牌DTO
     * @return 创建的品牌
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BrandDto>> createBrand(@Valid @RequestBody BrandDto brandDto) {
        log.info("创建新品牌请求: {}", brandDto.getName());
        BrandDto createdBrand = brandService.createBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BrandDto>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("品牌创建成功")
                        .data(createdBrand)
                        .build());
    }

    /**
     * 根据ID获取品牌
     *
     * @param id 品牌ID
     * @return 品牌信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandDto>> getBrandById(@PathVariable Long id) {
        log.info("获取品牌请求，ID: {}", id);
        BrandDto brand = brandService.getBrandById(id);
        return ResponseEntity.ok(ApiResponse.<BrandDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取品牌成功")
                .data(brand)
                .build());
    }

    /**
     * 根据slug获取品牌
     *
     * @param slug 品牌标识符
     * @return 品牌信息
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<BrandDto>> getBrandBySlug(@PathVariable String slug) {
        log.info("根据标识获取品牌请求: {}", slug);
        BrandDto brand = brandService.getBrandBySlug(slug);
        return ResponseEntity.ok(ApiResponse.<BrandDto>builder()
                .status(HttpStatus.OK.value())
                .message("获取品牌成功")
                .data(brand)
                .build());
    }

    /**
     * 获取所有品牌
     *
     * @return 品牌列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandDto>>> getAllBrands() {
        log.info("获取所有品牌请求");
        List<BrandDto> brands = brandService.getAllBrands();
        return ResponseEntity.ok(ApiResponse.<List<BrandDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取所有品牌成功")
                .data(brands)
                .build());
    }

    /**
     * 获取所有激活的品牌
     *
     * @return 品牌列表
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<BrandDto>>> getAllActiveBrands() {
        log.info("获取所有激活的品牌请求");
        List<BrandDto> brands = brandService.getAllActiveBrands();
        return ResponseEntity.ok(ApiResponse.<List<BrandDto>>builder()
                .status(HttpStatus.OK.value())
                .message("获取所有激活品牌成功")
                .data(brands)
                .build());
    }

    /**
     * 更新品牌
     *
     * @param id 品牌ID
     * @param brandDto 品牌DTO
     * @return 更新后的品牌
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandDto>> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandDto brandDto) {
        log.info("更新品牌请求，ID: {}", id);
        BrandDto updatedBrand = brandService.updateBrand(id, brandDto);
        return ResponseEntity.ok(ApiResponse.<BrandDto>builder()
                .status(HttpStatus.OK.value())
                .message("品牌更新成功")
                .data(updatedBrand)
                .build());
    }

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long id) {
        log.info("删除品牌请求，ID: {}", id);
        brandService.deleteBrand(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("品牌删除成功")
                .build());
    }

    /**
     * 搜索品牌
     *
     * @param keyword 搜索关键词
     * @return 品牌列表
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BrandDto>>> searchBrands(@RequestParam String keyword) {
        log.info("搜索品牌请求: {}", keyword);
        List<BrandDto> brands = brandService.searchBrands(keyword);
        return ResponseEntity.ok(ApiResponse.<List<BrandDto>>builder()
                .status(HttpStatus.OK.value())
                .message("品牌搜索成功")
                .data(brands)
                .build());
    }
}

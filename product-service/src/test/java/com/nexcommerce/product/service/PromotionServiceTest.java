package com.nexcommerce.product.service;

import com.nexcommerce.product.dto.PromotionDto;
import com.nexcommerce.product.mapper.PromotionMapper;
import com.nexcommerce.product.model.Promotion;
import com.nexcommerce.product.repository.CategoryRepository;
import com.nexcommerce.product.repository.ProductRepository;
import com.nexcommerce.product.repository.PromotionRepository;
import com.nexcommerce.product.service.impl.PromotionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PromotionMapper promotionMapper;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    private Promotion promotion;
    private PromotionDto promotionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        promotion = new Promotion();
        promotion.setId(1L);
        promotion.setName("测试促销");
        promotion.setDescription("测试促销描述");
        promotion.setType("discount");
        promotion.setDiscountRate(new BigDecimal("0.8"));
        promotion.setStartDate(LocalDateTime.now().minusDays(1));
        promotion.setEndDate(LocalDateTime.now().plusDays(10));
        promotion.setIsActive(true);
        
        promotionDto = new PromotionDto();
        promotionDto.setId(1L);
        promotionDto.setName("测试促销");
        promotionDto.setDescription("测试促销描述");
        promotionDto.setType("discount");
        promotionDto.setDiscountRate(new BigDecimal("0.8"));
        promotionDto.setStartDate(LocalDateTime.now().minusDays(1));
        promotionDto.setEndDate(LocalDateTime.now().plusDays(10));
        promotionDto.setIsActive(true);
    }

    @Test
    void createPromotion() {
        // 设置Mock行为
        when(promotionMapper.toEntity(any(PromotionDto.class))).thenReturn(promotion);
        when(promotionRepository.save(any(Promotion.class))).thenReturn(promotion);
        when(promotionMapper.toDto(any(Promotion.class))).thenReturn(promotionDto);
        
        // 调用被测试方法
        PromotionDto result = promotionService.createPromotion(promotionDto);
        
        // 断言
        assertNotNull(result);
        assertEquals("测试促销", result.getName());
        
        // 错误10: Mockito.verify方法内嵌套其他方法调用
        Mockito.verify(promotionRepository).save(Mockito.any(promotion.getClass()));
        
        // 正确的写法应该是:
        // Mockito.verify(promotionRepository).save(any(Promotion.class));
    }

    @Test
    void getPromotion() {
        // 设置Mock行为
        when(promotionRepository.findById(anyLong())).thenReturn(Optional.of(promotion));
        when(promotionMapper.toDto(any(Promotion.class))).thenReturn(promotionDto);
        
        // 调用被测试方法
        PromotionDto result = promotionService.getPromotion(1L);
        
        // 断言
        assertNotNull(result);
        assertEquals(1L, result.getId());
        
        // 验证
        verify(promotionRepository).findById(1L);
        verify(promotionMapper).toDto(promotion);
    }

    @Test
    void getAllPromotions() {
        // 设置Mock行为
        when(promotionRepository.findAll()).thenReturn(Arrays.asList(promotion));
        when(promotionMapper.toDtoList(anyList())).thenReturn(Arrays.asList(promotionDto));
        
        // 调用被测试方法
        List<PromotionDto> results = promotionService.getAllPromotions();
        
        // 断言
        assertNotNull(results);
        assertEquals(1, results.size());
        
        // 验证
        verify(promotionRepository).findAll();
        verify(promotionMapper).toDtoList(anyList());
    }

    @Test
    void getActivePromotions() {
        // 设置Mock行为
        when(promotionRepository.findActivePromotions(any(LocalDateTime.class))).thenReturn(Arrays.asList(promotion));
        when(promotionMapper.toDtoList(anyList())).thenReturn(Arrays.asList(promotionDto));
        
        // 调用被测试方法
        List<PromotionDto> results = promotionService.getActivePromotions();
        
        // 断言
        assertNotNull(results);
        assertEquals(1, results.size());
        
        // 错误10: Mockito.verify方法内嵌套其他方法调用
        Mockito.verify(promotionMapper).toDtoList(Mockito.anyList());
        
        // 正确的写法应该是:
        // Mockito.verify(promotionMapper).toDtoList(anyList());
    }

    @Test
    void updatePromotion() {
        // 设置Mock行为
        when(promotionRepository.findById(anyLong())).thenReturn(Optional.of(promotion));
        when(promotionRepository.save(any(Promotion.class))).thenReturn(promotion);
        when(promotionMapper.toDto(any(Promotion.class))).thenReturn(promotionDto);
        
        // 调用被测试方法
        PromotionDto result = promotionService.updatePromotion(1L, promotionDto);
        
        // 断言
        assertNotNull(result);
        assertEquals("测试促销", result.getName());
        
        // 验证
        verify(promotionRepository).findById(1L);
        verify(promotionMapper).updateEntityFromDto(eq(promotionDto), any(Promotion.class));
        verify(promotionRepository).save(any(Promotion.class));
        verify(promotionMapper).toDto(any(Promotion.class));
    }

    @Test
    void activatePromotion() {
        // 设置Mock行为
        when(promotionRepository.findById(anyLong())).thenReturn(Optional.of(promotion));
        when(promotionRepository.save(any(Promotion.class))).thenReturn(promotion);
        when(promotionMapper.toDto(any(Promotion.class))).thenReturn(promotionDto);
        
        // 调用被测试方法
        PromotionDto result = promotionService.activatePromotion(1L);
        
        // 断言
        assertNotNull(result);
        assertTrue(result.getIsActive());
        
        // 验证
        verify(promotionRepository).findById(1L);
        verify(promotionRepository).save(any(Promotion.class));
        verify(promotionMapper).toDto(any(Promotion.class));
    }

    @Test
    void isValidPromotionCode() {
        // 设置测试数据
        Promotion validPromotion = new Promotion();
        validPromotion.setId(1L);
        validPromotion.setCode("VALID10");
        validPromotion.setIsActive(true);
        validPromotion.setStartDate(LocalDateTime.now().minusDays(1));
        validPromotion.setEndDate(LocalDateTime.now().plusDays(10));
        validPromotion.setMaxUsageCount(100);
        validPromotion.setCurrentUsageCount(50);
        
        // 设置Mock行为
        when(promotionRepository.findByCode("VALID10")).thenReturn(Optional.of(validPromotion));
        
        // 调用被测试方法
        boolean result = promotionService.isValidPromotionCode("VALID10");
        
        // 断言
        assertTrue(result);
        
        // 验证
        verify(promotionRepository).findByCode("VALID10");
    }
}

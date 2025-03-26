package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.PromotionDto;
import com.nexcommerce.product.model.Promotion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T14:23:25+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public PromotionDto toDto(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionDto.PromotionDtoBuilder promotionDto = PromotionDto.builder();

        promotionDto.id( promotion.getId() );
        promotionDto.name( promotion.getName() );
        promotionDto.description( promotion.getDescription() );
        promotionDto.type( promotion.getType() );
        promotionDto.discountRate( promotion.getDiscountRate() );
        promotionDto.discountAmount( promotion.getDiscountAmount() );
        promotionDto.minPurchaseAmount( promotion.getMinPurchaseAmount() );
        promotionDto.maxDiscountAmount( promotion.getMaxDiscountAmount() );
        promotionDto.code( promotion.getCode() );
        promotionDto.startDate( promotion.getStartDate() );
        promotionDto.endDate( promotion.getEndDate() );
        promotionDto.isActive( promotion.getIsActive() );
        promotionDto.maxUsageCount( promotion.getMaxUsageCount() );
        promotionDto.currentUsageCount( promotion.getCurrentUsageCount() );
        promotionDto.createdAt( promotion.getCreatedAt() );
        promotionDto.updatedAt( promotion.getUpdatedAt() );

        return promotionDto.build();
    }

    @Override
    public Promotion toEntity(PromotionDto promotionDto) {
        if ( promotionDto == null ) {
            return null;
        }

        Promotion.PromotionBuilder promotion = Promotion.builder();

        promotion.id( promotionDto.getId() );
        promotion.name( promotionDto.getName() );
        promotion.description( promotionDto.getDescription() );
        promotion.type( promotionDto.getType() );
        promotion.discountRate( promotionDto.getDiscountRate() );
        promotion.discountAmount( promotionDto.getDiscountAmount() );
        promotion.minPurchaseAmount( promotionDto.getMinPurchaseAmount() );
        promotion.maxDiscountAmount( promotionDto.getMaxDiscountAmount() );
        promotion.code( promotionDto.getCode() );
        promotion.startDate( promotionDto.getStartDate() );
        promotion.endDate( promotionDto.getEndDate() );
        promotion.isActive( promotionDto.getIsActive() );
        promotion.maxUsageCount( promotionDto.getMaxUsageCount() );
        promotion.currentUsageCount( promotionDto.getCurrentUsageCount() );

        return promotion.build();
    }

    @Override
    public List<PromotionDto> toDtoList(List<Promotion> promotions) {
        if ( promotions == null ) {
            return null;
        }

        List<PromotionDto> list = new ArrayList<PromotionDto>( promotions.size() );
        for ( Promotion promotion : promotions ) {
            list.add( toDto( promotion ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(PromotionDto promotionDto, Promotion promotion) {
        if ( promotionDto == null ) {
            return;
        }

        promotion.setName( promotionDto.getName() );
        promotion.setDescription( promotionDto.getDescription() );
        promotion.setType( promotionDto.getType() );
        promotion.setDiscountRate( promotionDto.getDiscountRate() );
        promotion.setDiscountAmount( promotionDto.getDiscountAmount() );
        promotion.setMinPurchaseAmount( promotionDto.getMinPurchaseAmount() );
        promotion.setMaxDiscountAmount( promotionDto.getMaxDiscountAmount() );
        promotion.setCode( promotionDto.getCode() );
        promotion.setStartDate( promotionDto.getStartDate() );
        promotion.setEndDate( promotionDto.getEndDate() );
        promotion.setIsActive( promotionDto.getIsActive() );
        promotion.setMaxUsageCount( promotionDto.getMaxUsageCount() );
        promotion.setCurrentUsageCount( promotionDto.getCurrentUsageCount() );
    }
}

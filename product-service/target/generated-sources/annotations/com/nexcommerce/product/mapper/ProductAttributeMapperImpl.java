package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductAttributeDto;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.model.ProductAttribute;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T14:23:25+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class ProductAttributeMapperImpl implements ProductAttributeMapper {

    @Override
    public ProductAttributeDto toDto(ProductAttribute productAttribute) {
        if ( productAttribute == null ) {
            return null;
        }

        ProductAttributeDto.ProductAttributeDtoBuilder productAttributeDto = ProductAttributeDto.builder();

        productAttributeDto.productId( productAttributeProductId( productAttribute ) );
        productAttributeDto.id( productAttribute.getId() );
        productAttributeDto.attributeName( productAttribute.getAttributeName() );
        productAttributeDto.attributeValue( productAttribute.getAttributeValue() );
        productAttributeDto.attributeGroup( productAttribute.getAttributeGroup() );
        productAttributeDto.isFilterable( productAttribute.getIsFilterable() );
        productAttributeDto.isVisible( productAttribute.getIsVisible() );
        productAttributeDto.sortOrder( productAttribute.getSortOrder() );
        productAttributeDto.createdAt( productAttribute.getCreatedAt() );
        productAttributeDto.updatedAt( productAttribute.getUpdatedAt() );

        return productAttributeDto.build();
    }

    @Override
    public ProductAttribute toEntity(ProductAttributeDto productAttributeDto) {
        if ( productAttributeDto == null ) {
            return null;
        }

        ProductAttribute.ProductAttributeBuilder productAttribute = ProductAttribute.builder();

        productAttribute.id( productAttributeDto.getId() );
        productAttribute.attributeName( productAttributeDto.getAttributeName() );
        productAttribute.attributeValue( productAttributeDto.getAttributeValue() );
        productAttribute.attributeGroup( productAttributeDto.getAttributeGroup() );
        productAttribute.isFilterable( productAttributeDto.getIsFilterable() );
        productAttribute.isVisible( productAttributeDto.getIsVisible() );
        productAttribute.sortOrder( productAttributeDto.getSortOrder() );
        productAttribute.createdAt( productAttributeDto.getCreatedAt() );
        productAttribute.updatedAt( productAttributeDto.getUpdatedAt() );

        return productAttribute.build();
    }

    @Override
    public Set<ProductAttributeDto> toDtoSet(Set<ProductAttribute> productAttributes) {
        if ( productAttributes == null ) {
            return null;
        }

        Set<ProductAttributeDto> set = new LinkedHashSet<ProductAttributeDto>( Math.max( (int) ( productAttributes.size() / .75f ) + 1, 16 ) );
        for ( ProductAttribute productAttribute : productAttributes ) {
            set.add( toDto( productAttribute ) );
        }

        return set;
    }

    @Override
    public Set<ProductAttribute> toEntitySet(Set<ProductAttributeDto> productAttributeDtos) {
        if ( productAttributeDtos == null ) {
            return null;
        }

        Set<ProductAttribute> set = new LinkedHashSet<ProductAttribute>( Math.max( (int) ( productAttributeDtos.size() / .75f ) + 1, 16 ) );
        for ( ProductAttributeDto productAttributeDto : productAttributeDtos ) {
            set.add( toEntity( productAttributeDto ) );
        }

        return set;
    }

    @Override
    public List<ProductAttributeDto> toDtoList(List<ProductAttribute> productAttributes) {
        if ( productAttributes == null ) {
            return null;
        }

        List<ProductAttributeDto> list = new ArrayList<ProductAttributeDto>( productAttributes.size() );
        for ( ProductAttribute productAttribute : productAttributes ) {
            list.add( toDto( productAttribute ) );
        }

        return list;
    }

    @Override
    public List<ProductAttribute> toEntityList(List<ProductAttributeDto> productAttributeDtos) {
        if ( productAttributeDtos == null ) {
            return null;
        }

        List<ProductAttribute> list = new ArrayList<ProductAttribute>( productAttributeDtos.size() );
        for ( ProductAttributeDto productAttributeDto : productAttributeDtos ) {
            list.add( toEntity( productAttributeDto ) );
        }

        return list;
    }

    @Override
    public void updateProductAttributeFromDto(ProductAttributeDto productAttributeDto, ProductAttribute productAttribute) {
        if ( productAttributeDto == null ) {
            return;
        }

        if ( productAttributeDto.getAttributeName() != null ) {
            productAttribute.setAttributeName( productAttributeDto.getAttributeName() );
        }
        if ( productAttributeDto.getAttributeValue() != null ) {
            productAttribute.setAttributeValue( productAttributeDto.getAttributeValue() );
        }
        if ( productAttributeDto.getAttributeGroup() != null ) {
            productAttribute.setAttributeGroup( productAttributeDto.getAttributeGroup() );
        }
        if ( productAttributeDto.getIsFilterable() != null ) {
            productAttribute.setIsFilterable( productAttributeDto.getIsFilterable() );
        }
        if ( productAttributeDto.getIsVisible() != null ) {
            productAttribute.setIsVisible( productAttributeDto.getIsVisible() );
        }
        if ( productAttributeDto.getSortOrder() != null ) {
            productAttribute.setSortOrder( productAttributeDto.getSortOrder() );
        }
        if ( productAttributeDto.getUpdatedAt() != null ) {
            productAttribute.setUpdatedAt( productAttributeDto.getUpdatedAt() );
        }
    }

    private Long productAttributeProductId(ProductAttribute productAttribute) {
        if ( productAttribute == null ) {
            return null;
        }
        Product product = productAttribute.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

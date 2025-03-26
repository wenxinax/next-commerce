package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductImageDto;
import com.nexcommerce.product.model.Product;
import com.nexcommerce.product.model.ProductImage;
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
public class ProductImageMapperImpl implements ProductImageMapper {

    @Override
    public ProductImageDto toDto(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }

        ProductImageDto.ProductImageDtoBuilder productImageDto = ProductImageDto.builder();

        productImageDto.productId( productImageProductId( productImage ) );
        productImageDto.id( productImage.getId() );
        productImageDto.imageUrl( productImage.getImageUrl() );
        productImageDto.altText( productImage.getAltText() );
        productImageDto.isPrimary( productImage.getIsPrimary() );
        productImageDto.sortOrder( productImage.getSortOrder() );
        productImageDto.createdAt( productImage.getCreatedAt() );
        productImageDto.updatedAt( productImage.getUpdatedAt() );

        return productImageDto.build();
    }

    @Override
    public ProductImage toEntity(ProductImageDto productImageDto) {
        if ( productImageDto == null ) {
            return null;
        }

        ProductImage.ProductImageBuilder productImage = ProductImage.builder();

        productImage.id( productImageDto.getId() );
        productImage.imageUrl( productImageDto.getImageUrl() );
        productImage.altText( productImageDto.getAltText() );
        productImage.isPrimary( productImageDto.getIsPrimary() );
        productImage.sortOrder( productImageDto.getSortOrder() );
        productImage.createdAt( productImageDto.getCreatedAt() );
        productImage.updatedAt( productImageDto.getUpdatedAt() );

        return productImage.build();
    }

    @Override
    public Set<ProductImageDto> toDtoSet(Set<ProductImage> productImages) {
        if ( productImages == null ) {
            return null;
        }

        Set<ProductImageDto> set = new LinkedHashSet<ProductImageDto>( Math.max( (int) ( productImages.size() / .75f ) + 1, 16 ) );
        for ( ProductImage productImage : productImages ) {
            set.add( toDto( productImage ) );
        }

        return set;
    }

    @Override
    public Set<ProductImage> toEntitySet(Set<ProductImageDto> productImageDtos) {
        if ( productImageDtos == null ) {
            return null;
        }

        Set<ProductImage> set = new LinkedHashSet<ProductImage>( Math.max( (int) ( productImageDtos.size() / .75f ) + 1, 16 ) );
        for ( ProductImageDto productImageDto : productImageDtos ) {
            set.add( toEntity( productImageDto ) );
        }

        return set;
    }

    @Override
    public List<ProductImageDto> toDtoList(List<ProductImage> productImages) {
        if ( productImages == null ) {
            return null;
        }

        List<ProductImageDto> list = new ArrayList<ProductImageDto>( productImages.size() );
        for ( ProductImage productImage : productImages ) {
            list.add( toDto( productImage ) );
        }

        return list;
    }

    @Override
    public List<ProductImage> toEntityList(List<ProductImageDto> productImageDtos) {
        if ( productImageDtos == null ) {
            return null;
        }

        List<ProductImage> list = new ArrayList<ProductImage>( productImageDtos.size() );
        for ( ProductImageDto productImageDto : productImageDtos ) {
            list.add( toEntity( productImageDto ) );
        }

        return list;
    }

    @Override
    public void updateProductImageFromDto(ProductImageDto productImageDto, ProductImage productImage) {
        if ( productImageDto == null ) {
            return;
        }

        if ( productImageDto.getImageUrl() != null ) {
            productImage.setImageUrl( productImageDto.getImageUrl() );
        }
        if ( productImageDto.getAltText() != null ) {
            productImage.setAltText( productImageDto.getAltText() );
        }
        if ( productImageDto.getIsPrimary() != null ) {
            productImage.setIsPrimary( productImageDto.getIsPrimary() );
        }
        if ( productImageDto.getSortOrder() != null ) {
            productImage.setSortOrder( productImageDto.getSortOrder() );
        }
        if ( productImageDto.getUpdatedAt() != null ) {
            productImage.setUpdatedAt( productImageDto.getUpdatedAt() );
        }
    }

    private Long productImageProductId(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }
        Product product = productImage.getProduct();
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

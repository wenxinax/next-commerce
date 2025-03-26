package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.BrandDto;
import com.nexcommerce.product.model.Brand;
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
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandDto toDto(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandDto.BrandDtoBuilder brandDto = BrandDto.builder();

        brandDto.id( brand.getId() );
        brandDto.name( brand.getName() );
        brandDto.slug( brand.getSlug() );
        brandDto.description( brand.getDescription() );
        brandDto.logoUrl( brand.getLogoUrl() );
        brandDto.websiteUrl( brand.getWebsiteUrl() );
        brandDto.isActive( brand.getIsActive() );
        brandDto.sortOrder( brand.getSortOrder() );
        brandDto.createdAt( brand.getCreatedAt() );
        brandDto.updatedAt( brand.getUpdatedAt() );

        return brandDto.build();
    }

    @Override
    public Brand toEntity(BrandDto brandDto) {
        if ( brandDto == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.id( brandDto.getId() );
        brand.name( brandDto.getName() );
        brand.slug( brandDto.getSlug() );
        brand.description( brandDto.getDescription() );
        brand.logoUrl( brandDto.getLogoUrl() );
        brand.websiteUrl( brandDto.getWebsiteUrl() );
        brand.isActive( brandDto.getIsActive() );
        brand.sortOrder( brandDto.getSortOrder() );
        brand.createdAt( brandDto.getCreatedAt() );
        brand.updatedAt( brandDto.getUpdatedAt() );

        return brand.build();
    }

    @Override
    public List<BrandDto> toDtoList(List<Brand> brands) {
        if ( brands == null ) {
            return null;
        }

        List<BrandDto> list = new ArrayList<BrandDto>( brands.size() );
        for ( Brand brand : brands ) {
            list.add( toDto( brand ) );
        }

        return list;
    }

    @Override
    public List<Brand> toEntityList(List<BrandDto> brandDtos) {
        if ( brandDtos == null ) {
            return null;
        }

        List<Brand> list = new ArrayList<Brand>( brandDtos.size() );
        for ( BrandDto brandDto : brandDtos ) {
            list.add( toEntity( brandDto ) );
        }

        return list;
    }

    @Override
    public void updateBrandFromDto(BrandDto brandDto, Brand brand) {
        if ( brandDto == null ) {
            return;
        }

        if ( brandDto.getName() != null ) {
            brand.setName( brandDto.getName() );
        }
        if ( brandDto.getSlug() != null ) {
            brand.setSlug( brandDto.getSlug() );
        }
        if ( brandDto.getDescription() != null ) {
            brand.setDescription( brandDto.getDescription() );
        }
        if ( brandDto.getLogoUrl() != null ) {
            brand.setLogoUrl( brandDto.getLogoUrl() );
        }
        if ( brandDto.getWebsiteUrl() != null ) {
            brand.setWebsiteUrl( brandDto.getWebsiteUrl() );
        }
        if ( brandDto.getIsActive() != null ) {
            brand.setIsActive( brandDto.getIsActive() );
        }
        if ( brandDto.getSortOrder() != null ) {
            brand.setSortOrder( brandDto.getSortOrder() );
        }
        if ( brandDto.getUpdatedAt() != null ) {
            brand.setUpdatedAt( brandDto.getUpdatedAt() );
        }
    }
}

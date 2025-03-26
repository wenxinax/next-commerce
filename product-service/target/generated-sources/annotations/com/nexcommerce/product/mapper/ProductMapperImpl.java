package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.ProductDto;
import com.nexcommerce.product.model.Brand;
import com.nexcommerce.product.model.Category;
import com.nexcommerce.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T14:23:25+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    private final ProductImageMapper productImageMapper;
    private final ProductAttributeMapper productAttributeMapper;

    @Autowired
    public ProductMapperImpl(ProductImageMapper productImageMapper, ProductAttributeMapper productAttributeMapper) {

        this.productImageMapper = productImageMapper;
        this.productAttributeMapper = productAttributeMapper;
    }

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.categoryId( productCategoryId( product ) );
        productDto.categoryName( productCategoryName( product ) );
        productDto.brandId( productBrandId( product ) );
        productDto.brandName( productBrandName( product ) );
        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.sku( product.getSku() );
        productDto.description( product.getDescription() );
        productDto.price( product.getPrice() );
        productDto.salePrice( product.getSalePrice() );
        productDto.quantity( product.getQuantity() );
        productDto.status( product.getStatus() );
        productDto.images( productImageMapper.toDtoSet( product.getImages() ) );
        productDto.attributes( productAttributeMapper.toDtoSet( product.getAttributes() ) );
        productDto.isFeatured( product.getIsFeatured() );
        productDto.weight( product.getWeight() );
        productDto.length( product.getLength() );
        productDto.width( product.getWidth() );
        productDto.height( product.getHeight() );
        productDto.createdAt( product.getCreatedAt() );
        productDto.updatedAt( product.getUpdatedAt() );

        return productDto.build();
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productDto.getId() );
        product.name( productDto.getName() );
        product.sku( productDto.getSku() );
        product.description( productDto.getDescription() );
        product.price( productDto.getPrice() );
        product.salePrice( productDto.getSalePrice() );
        product.quantity( productDto.getQuantity() );
        product.status( productDto.getStatus() );
        product.isFeatured( productDto.getIsFeatured() );
        product.weight( productDto.getWeight() );
        product.length( productDto.getLength() );
        product.width( productDto.getWidth() );
        product.height( productDto.getHeight() );
        product.createdAt( productDto.getCreatedAt() );
        product.updatedAt( productDto.getUpdatedAt() );

        return product.build();
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public List<Product> toEntityList(List<ProductDto> productDtos) {
        if ( productDtos == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDtos.size() );
        for ( ProductDto productDto : productDtos ) {
            list.add( toEntity( productDto ) );
        }

        return list;
    }

    @Override
    public void updateProductFromDto(ProductDto productDto, Product product) {
        if ( productDto == null ) {
            return;
        }

        if ( productDto.getName() != null ) {
            product.setName( productDto.getName() );
        }
        if ( productDto.getSku() != null ) {
            product.setSku( productDto.getSku() );
        }
        if ( productDto.getDescription() != null ) {
            product.setDescription( productDto.getDescription() );
        }
        if ( productDto.getPrice() != null ) {
            product.setPrice( productDto.getPrice() );
        }
        if ( productDto.getSalePrice() != null ) {
            product.setSalePrice( productDto.getSalePrice() );
        }
        if ( productDto.getQuantity() != null ) {
            product.setQuantity( productDto.getQuantity() );
        }
        if ( productDto.getStatus() != null ) {
            product.setStatus( productDto.getStatus() );
        }
        if ( productDto.getIsFeatured() != null ) {
            product.setIsFeatured( productDto.getIsFeatured() );
        }
        if ( productDto.getWeight() != null ) {
            product.setWeight( productDto.getWeight() );
        }
        if ( productDto.getLength() != null ) {
            product.setLength( productDto.getLength() );
        }
        if ( productDto.getWidth() != null ) {
            product.setWidth( productDto.getWidth() );
        }
        if ( productDto.getHeight() != null ) {
            product.setHeight( productDto.getHeight() );
        }
        if ( productDto.getUpdatedAt() != null ) {
            product.setUpdatedAt( productDto.getUpdatedAt() );
        }
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long productBrandId(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        Long id = brand.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productBrandName(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

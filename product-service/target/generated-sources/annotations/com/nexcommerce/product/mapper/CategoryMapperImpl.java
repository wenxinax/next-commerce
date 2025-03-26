package com.nexcommerce.product.mapper;

import com.nexcommerce.product.dto.CategoryDto;
import com.nexcommerce.product.model.Category;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T14:25:56+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.parentId( categoryParentId( category ) );
        categoryDto.parentName( categoryParentName( category ) );
        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );
        categoryDto.slug( category.getSlug() );
        categoryDto.description( category.getDescription() );
        categoryDto.imageUrl( category.getImageUrl() );
        categoryDto.children( toDtoSet( category.getChildren() ) );
        categoryDto.isActive( category.getIsActive() );
        categoryDto.sortOrder( category.getSortOrder() );
        categoryDto.createdAt( category.getCreatedAt() );
        categoryDto.updatedAt( category.getUpdatedAt() );

        return categoryDto.build();
    }

    @Override
    public Category toEntity(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryDto.getId() );
        category.name( categoryDto.getName() );
        category.slug( categoryDto.getSlug() );
        category.description( categoryDto.getDescription() );
        category.imageUrl( categoryDto.getImageUrl() );
        category.isActive( categoryDto.getIsActive() );
        category.sortOrder( categoryDto.getSortOrder() );
        category.createdAt( categoryDto.getCreatedAt() );
        category.updatedAt( categoryDto.getUpdatedAt() );

        return category.build();
    }

    @Override
    public List<CategoryDto> toDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( toDto( category ) );
        }

        return list;
    }

    @Override
    public Set<CategoryDto> toDtoSet(Set<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        Set<CategoryDto> set = new LinkedHashSet<CategoryDto>( Math.max( (int) ( categories.size() / .75f ) + 1, 16 ) );
        for ( Category category : categories ) {
            set.add( toDto( category ) );
        }

        return set;
    }

    @Override
    public List<Category> toEntityList(List<CategoryDto> categoryDtos) {
        if ( categoryDtos == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( categoryDtos.size() );
        for ( CategoryDto categoryDto : categoryDtos ) {
            list.add( toEntity( categoryDto ) );
        }

        return list;
    }

    @Override
    public void updateCategoryFromDto(CategoryDto categoryDto, Category category) {
        if ( categoryDto == null ) {
            return;
        }

        if ( categoryDto.getName() != null ) {
            category.setName( categoryDto.getName() );
        }
        if ( categoryDto.getSlug() != null ) {
            category.setSlug( categoryDto.getSlug() );
        }
        if ( categoryDto.getDescription() != null ) {
            category.setDescription( categoryDto.getDescription() );
        }
        if ( categoryDto.getImageUrl() != null ) {
            category.setImageUrl( categoryDto.getImageUrl() );
        }
        if ( categoryDto.getIsActive() != null ) {
            category.setIsActive( categoryDto.getIsActive() );
        }
        if ( categoryDto.getSortOrder() != null ) {
            category.setSortOrder( categoryDto.getSortOrder() );
        }
        if ( categoryDto.getUpdatedAt() != null ) {
            category.setUpdatedAt( categoryDto.getUpdatedAt() );
        }
    }

    private Long categoryParentId(Category category) {
        if ( category == null ) {
            return null;
        }
        Category parent = category.getParent();
        if ( parent == null ) {
            return null;
        }
        Long id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String categoryParentName(Category category) {
        if ( category == null ) {
            return null;
        }
        Category parent = category.getParent();
        if ( parent == null ) {
            return null;
        }
        String name = parent.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

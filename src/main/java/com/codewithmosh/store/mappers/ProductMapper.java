package com.codewithmosh.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.codewithmosh.store.DTOs.ProductDto;
import com.codewithmosh.store.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void update(ProductDto dto, @MappingTarget Product product);
}

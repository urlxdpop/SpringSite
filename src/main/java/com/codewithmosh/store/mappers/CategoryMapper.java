package com.codewithmosh.store.mappers;

import org.mapstruct.Mapper;

import com.codewithmosh.store.DTOs.CategoryDto;
import com.codewithmosh.store.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}

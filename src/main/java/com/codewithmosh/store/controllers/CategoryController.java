package com.codewithmosh.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.DTOs.CategoryDto;
import com.codewithmosh.store.mappers.CategoryMapper;
import com.codewithmosh.store.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public Iterable<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Byte id) {
        var category = categoryRepository.findById(id);

        if (category.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(categoryMapper.toDto(category.get()));
    }
}

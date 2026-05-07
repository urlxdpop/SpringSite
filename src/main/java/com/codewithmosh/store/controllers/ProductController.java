package com.codewithmosh.store.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.codewithmosh.store.DTOs.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "categoryId") Byte categoryId) {
        List<Product> products;

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        if (id <= 0)
            return ResponseEntity.notFound().build();
        ;

        var product = productRepository.findById(id);

        if (product.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productMapper.toDto(product.get()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriComponentsBuilder) {
        var product = productMapper.toEntity(request);

        if (request.categoryId != null) {
            var category = categoryRepository.findById(request.categoryId);
            if (category.isEmpty())
                return ResponseEntity.notFound().build();
            product.setCategory(category.get());
        }

        productRepository.save(product);

        var productDto = productMapper.toDto(product);
        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto request) {
        var product = productRepository.findById(id);

        if (product.isEmpty())
            return ResponseEntity.notFound().build();

        productMapper.update(request, product.get());

        if (request.categoryId != null) {
            var category = categoryRepository.findById(request.categoryId);
            if (category.isEmpty())
                return ResponseEntity.notFound().build();
            product.get().setCategory(category.get());
        }

        productRepository.save(product.get());

        return ResponseEntity.ok(productMapper.toDto(product.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id);

        if (product.isEmpty())
            return ResponseEntity.notFound().build();

        productRepository.delete(product.get());

        return ResponseEntity.noContent().build();
    }
}

package com.codewithmosh.store.DTOs;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDto {
    public Long id;
    public String name;
    public String description;
    public BigDecimal price;
    public Byte categoryId;
}

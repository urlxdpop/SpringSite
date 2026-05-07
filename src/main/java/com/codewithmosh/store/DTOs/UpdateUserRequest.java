package com.codewithmosh.store.DTOs;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}

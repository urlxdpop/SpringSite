package com.codewithmosh.store.DTOs;

import lombok.Data;

@Data
public class RegisterUserRequest {
    public String name;
    public String email;
    public String password;
}

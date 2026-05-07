package com.codewithmosh.store.DTOs;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    public String oldPassword;
    public String newPassword;
}

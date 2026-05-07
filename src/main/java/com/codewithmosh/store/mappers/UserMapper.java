package com.codewithmosh.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.codewithmosh.store.DTOs.RegisterUserRequest;
import com.codewithmosh.store.DTOs.UpdateUserRequest;
import com.codewithmosh.store.DTOs.UserDto;
import com.codewithmosh.store.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}

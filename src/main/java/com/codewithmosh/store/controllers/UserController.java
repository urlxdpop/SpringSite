package com.codewithmosh.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.DTOs.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public Iterable<UserDto> getAllUsers(){
        return userRepository.findAll()
        .stream()
        .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
        .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return userRepository.findById(id)
        .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
}

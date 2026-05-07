package com.codewithmosh.store.controllers;

import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.codewithmosh.store.DTOs.RegisterUserRequest;
import com.codewithmosh.store.DTOs.UpdateUserRequest;
import com.codewithmosh.store.DTOs.UserDto;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
        @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ){
        if (!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAll(Sort.by(sort))
        .stream()
        .map(userMapper::toDto)
        .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        var user = userRepository.findById(id);

        if (user == null)
            return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok(userMapper.toDto(user.get()));
    }

    @PostMapping
    public ResponseEntity<UserDto> RegisterUser(
        @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriComponentsBuilder
    ){
        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable Long id,
        @RequestBody UpdateUserRequest request
    ){
        var user = userRepository.findById(id);

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        userMapper.update(request, user.get());
        userRepository.save(user.get());

        return ResponseEntity.ok(userMapper.toDto(user.get()));
    }
}

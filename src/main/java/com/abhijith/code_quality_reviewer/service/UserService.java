package com.abhijith.code_quality_reviewer.service;

import com.abhijith.code_quality_reviewer.dto.UserDto;
import com.abhijith.code_quality_reviewer.entity.User;
import com.abhijith.code_quality_reviewer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        // 1. Validate input efficiently
        if (userRepo.findByFullName(userDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");
        }
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists.");
        }

        // 2. Create the user entity
        User user = User.builder().
                fullName(userDto.getUsername()).
                email(userDto.getEmail()).
                password(passwordEncoder.encode(userDto.getPassword()))
                .role(User.Role.USER).build();
        ;

        User savedUser = userRepo.save(user);

        // 3. Return a DTO of the created user
        return toDto(savedUser);
    }

    // The loginUser method is REMOVED.

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getFullName());
        dto.setEmail(user.getEmail());
        // Important: Never include the password in a DTO
        return dto;
    }
}
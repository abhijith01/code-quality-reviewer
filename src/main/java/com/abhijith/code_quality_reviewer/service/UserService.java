package com.abhijith.code_quality_reviewer.service;

import com.abhijith.code_quality_reviewer.dto.UserDto;
import com.abhijith.code_quality_reviewer.entity.User;
import com.abhijith.code_quality_reviewer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private ResponseEntity<String> validate(String email,String name){
        ResponseEntity<String> response = null;
        if(userRepo.findByEmail(email).isPresent() && userRepo.findByFullName(name).isPresent()){
            response = new ResponseEntity<>("user already exists please login",HttpStatus.BAD_REQUEST);
        } else if(userRepo.findByEmail(email).isPresent()) {
            response = new ResponseEntity<>("please try a differnet email",HttpStatus.BAD_REQUEST);
        } else if (userRepo.findByFullName(name).isPresent()) {
            response = new ResponseEntity<>("please try a different username",HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    public ResponseEntity<String>  createUser(UserDto userDto) {
        User user = new User();
        ResponseEntity<String> response = null;
        response = validate(userDto.getEmail(), userDto.getUsername());
        user.setFullName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.USER);// Set a default role
        if(response==null) {
            userRepo.save(user);
        }
         return response==null ? new ResponseEntity<>(toDto(user).toString(), HttpStatus.CREATED):response;
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getFullName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}

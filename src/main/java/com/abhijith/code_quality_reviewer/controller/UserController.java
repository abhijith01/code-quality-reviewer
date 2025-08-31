package com.abhijith.code_quality_reviewer.controller;

import com.abhijith.code_quality_reviewer.dto.UserDto;
import com.abhijith.code_quality_reviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createUser(@RequestParam("username") String username,
                                             @RequestParam("email") String email,
                                             @RequestParam("password") String password) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setPassword(password);
        return userService.createUser(userDto);
    }
    @GetMapping(value = "/principal")
    public String ussername(){
        String username = Principal.class.getName();
        return username ;
    }


}

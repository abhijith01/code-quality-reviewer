package com.abhijith.code_quality_reviewer.controller;

import com.abhijith.code_quality_reviewer.dto.UserDto;
import com.abhijith.code_quality_reviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}

package com.abhijith.code_quality_reviewer.controller;

import com.abhijith.code_quality_reviewer.dto.UserDto;
import com.abhijith.code_quality_reviewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // The /login endpoint is REMOVED. Spring Security's formLogin() handles it.

    @GetMapping(value = "/me")
    public String currentUserName(Principal principal){
        // This will now work correctly after a successful login!
        if(principal == null){
            // This case should not be reachable if security is configured correctly,
            // as unauthenticated requests will be denied.
            return "No user logged in...";
        }
        System.out.println("Principal: " + principal.toString());
        return "Currently logged in user: " + principal.getName();
    }
}
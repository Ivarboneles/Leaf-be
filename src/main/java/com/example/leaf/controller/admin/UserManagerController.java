package com.example.leaf.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class UserManagerController {
    @GetMapping(value = "disable/user/{username}")
    public ResponseEntity<?> disableUser(@PathVariable(value = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "enable/user/{username}")
    public ResponseEntity<?> enableUser(@PathVariable(value = "username") String username){
        return ResponseEntity.ok().build();
    }
}

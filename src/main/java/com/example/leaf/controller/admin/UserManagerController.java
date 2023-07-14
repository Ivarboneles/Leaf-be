package com.example.leaf.controller.admin;

import com.example.leaf.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class UserManagerController {

    @Autowired
    UserService userService;

    @PostMapping(value = "user/disable/{username}")
    public ResponseEntity<?> disableUser(@PathVariable(value = "username") String username){
        return ResponseEntity.ok(userService.disableUser(username));
    }

    @PostMapping(value = "user/enable/{username}")
    public ResponseEntity<?> enableUser(@PathVariable(value = "username") String username){
        return ResponseEntity.ok(userService.enableUser(username));
    }

    @GetMapping(value = "/all-user/{page}")
    public ResponseEntity<?> getListUserOfPage(@PathVariable(value = "page") Integer page){
        return ResponseEntity.ok(userService.getListUserOfPage(page));
    }
}

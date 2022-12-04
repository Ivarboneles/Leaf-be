package com.example.leaf.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/statistic")
public class StatisticController {
    @GetMapping(value = "/user/current")
    public ResponseEntity<?> amountCurrentUser(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user/new")
    public ResponseEntity<?> amountNewUserInDate(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user/online")
    public ResponseEntity<?> amountCurrentUserOnline(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user/enable")
    public ResponseEntity<?> amountCurrentUserEnable(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/post/current")
    public ResponseEntity<?> amountCurrentPost(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/post/new")
    public ResponseEntity<?> amountNewPostInDate(){
        return ResponseEntity.ok().build();
    }
}

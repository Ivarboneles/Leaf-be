package com.example.leaf.controller.admin;

import com.example.leaf.services.UserService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/statistic")
public class StatisticController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/get-data")
    public ResponseEntity<?> getStatisticData(){
        return ResponseEntity.ok(
                userService.getStatisticData()
        );
    }

}

package com.example.leaf.controller.admin;

import com.example.leaf.dto.request.PostRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class PostManagerController {
    @GetMapping(value = "/disable/post/{id}")
    public ResponseEntity<?> disableUser(@PathVariable(value = "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/enable/post/{id}")
    public ResponseEntity<?> enableUser(@PathVariable(value = "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/change-content/{id}")
    public ResponseEntity<?> changeContentPost(@PathVariable(value = "id") UUID id,
                                               @RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok().build();
    }
}

package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.NotifyRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/notify")
public class NotifyController {
    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllNotifyByUser(@PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createNotify(@RequestBody NotifyRequestDTO notifyRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateNotify(@RequestBody NotifyRequestDTO notifyRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteNotify(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }
}

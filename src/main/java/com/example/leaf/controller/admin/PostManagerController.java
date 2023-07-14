package com.example.leaf.controller.admin;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class PostManagerController {

    @Autowired
    PostService postService;
    @PostMapping(value = "post/disable/{id}")
    public ResponseEntity<?> disablePost(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(postService.disablePost(id));
    }
    @PostMapping(value = "post/enable/{username}")
    public ResponseEntity<?> enablePost(@PathVariable(value = "id")  String id){
        return ResponseEntity.ok(postService.enablePost(id));
    }

    @GetMapping(value = "/all-post/{page}")
    public ResponseEntity<?> getListPostOfPage(@PathVariable(value = "page") Integer page){
        return ResponseEntity.ok(postService.getListPostOfPage(page));
    }
}

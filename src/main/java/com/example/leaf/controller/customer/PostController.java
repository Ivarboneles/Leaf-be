package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getAllPostByUser(@PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/share/{id}")
    public ResponseEntity<?> sharePost( @PathVariable(name = "id") UUID id,
                                           @RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reaction/{id}")
    public ResponseEntity<?> reactionPost( @PathVariable(name = "id") UUID id,
                                              @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/un-reaction/{id}")
    public ResponseEntity<?> unReactionPost( @PathVariable(name = "id") UUID id,
                                                @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }
}

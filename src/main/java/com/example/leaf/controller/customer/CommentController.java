package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ChatRequestDTO;
import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<?> getAllCommentByPost(@PathVariable(name = "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/rep/{id}")
    public ResponseEntity<?> replyComment( @PathVariable(name = "id") UUID id,
                                        @RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reaction/{id}")
    public ResponseEntity<?> reactionComment( @PathVariable(name = "id") UUID id,
                                           @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/un-reaction/{id}")
    public ResponseEntity<?> unReactionComment( @PathVariable(name = "id") UUID id,
                                             @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }
}

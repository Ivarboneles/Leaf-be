package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ChatRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/chat")
public class ChatController {

    @GetMapping
    public ResponseEntity<?> getAllChatByUser(@RequestParam(name = "userTo") String userTo,
                                                 @RequestParam(name = "userFrom")String userFrom){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequestDTO chatRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/rep/{id}")
    public ResponseEntity<?> replyChat( @PathVariable(name = "id") UUID id,
                                        @RequestBody ChatRequestDTO chatRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reaction/{id}")
    public ResponseEntity<?> reactionChat( @PathVariable(name = "id") UUID id,
                                        @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/un-reaction/{id}")
    public ResponseEntity<?> unReactionChat( @PathVariable(name = "id") UUID id,
                                           @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }
}

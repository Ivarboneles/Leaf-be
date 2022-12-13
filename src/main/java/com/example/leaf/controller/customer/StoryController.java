package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.request.StoryRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/story")
public class StoryController {

    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllStoryByUser( @PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStoryById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createStory(@RequestBody StoryRequestDTO storyRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateStory(@RequestBody StoryRequestDTO storyRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/share/{id}")
    public ResponseEntity<?> shareStory( @PathVariable(name = "id") UUID id,
                                           @RequestBody StoryRequestDTO storyRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reaction/{id}")
    public ResponseEntity<?> reactionStory( @PathVariable(name = "id") UUID id,
                                              @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/un-reaction/{id}")
    public ResponseEntity<?> unReactionStory( @PathVariable(name = "id") UUID id,
                                                @RequestBody ReactionRequestDTO reactionRequestDTO){
        return ResponseEntity.ok().build();
    }
}

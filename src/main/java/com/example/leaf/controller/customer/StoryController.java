package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.request.StoryRequestDTO;
import com.example.leaf.services.StoryService;
import com.example.leaf.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/story")
public class StoryController {

    @Autowired
    StoryService storyService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllStoryByUser( @PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStoryById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(encoding = @Encoding(name = "storyRequestDTO", contentType = "application/json")))
    public ResponseEntity<?> createStory(@RequestBody StoryRequestDTO storyRequestDTO,
                                         @RequestPart(value = "files", required = false) MultipartFile[] files,
                                         HttpServletRequest request){
        return ResponseEntity.ok(storyService.createStory(
                storyRequestDTO,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                files
                ));
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

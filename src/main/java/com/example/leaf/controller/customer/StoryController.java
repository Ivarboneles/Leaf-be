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
    public ResponseEntity<?> getAllStoryByUser( HttpServletRequest request){
        return ResponseEntity.ok(
                storyService.getAllStoryByUser(
                        jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                )
        );
    }

    @GetMapping(value = "/list-story")
    public ResponseEntity<?> getListStory(HttpServletRequest request){
        return ResponseEntity.ok(
                storyService.getListStory(
                        jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                )
        );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStoryById(@PathVariable(name= "id") String id){
        return ResponseEntity.ok(
            storyService.getStoryById(id)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(encoding = @Encoding(name = "storyRequestDTO", contentType = "application/json")))
    public ResponseEntity<?> createStory(@RequestBody MultipartFile[] files,
                                         HttpServletRequest request){
        return ResponseEntity.ok(storyService.createStory(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                files
                ));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable(name= "id") String id){
        return ResponseEntity.ok(
            storyService.deletetStory(id)
        );
    }

}

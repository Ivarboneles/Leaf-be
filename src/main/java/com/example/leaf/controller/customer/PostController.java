package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.PostService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {

    @Autowired
    ImageService imageService;

    @Autowired
    PostService postService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllPostByUser(@PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO postRequestDTO,
                                        HttpServletRequest request){
        return ResponseEntity.ok(postService.createPost(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                postRequestDTO
        ));
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFilePost(@PathVariable("id") String id,
                                            @RequestPart MultipartFile[] files){
        return ResponseEntity.ok(postService.uploadFilePost(id, files));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") String id,
                                        @RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok(postService.updatePost(id,postRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name= "id") String id){
        return ResponseEntity.ok(postService.deletePost(id));
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

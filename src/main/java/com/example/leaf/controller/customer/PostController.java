package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.PostService;
import com.example.leaf.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
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
    public ResponseEntity<?> getListPostOfCurrentUser(HttpServletRequest request){
        return ResponseEntity.ok(postService.getListPostOfUser(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @GetMapping(value = "/all/user")
    public ResponseEntity<?> getAllPostOfCurrentUser(HttpServletRequest request){
        return ResponseEntity.ok(postService.getAllPostOfUser(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @GetMapping(value = "/new-feed/{page}")
    public ResponseEntity<?> getNewFeedPost( @PathVariable("page") Integer page,
                                             HttpServletRequest request){
        return ResponseEntity.ok(postService.getNewFeedPost(
              page
        ));
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getAllPostByUser( @PathVariable("username") String username){
        return ResponseEntity.ok(postService.getListPostOfUserName(username));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(encoding = @Encoding(name = "postRequestDTO", contentType = "application/json")))
    public ResponseEntity<?> createPost(@RequestPart("info") PostRequestDTO postRequestDTO,
                                        @RequestPart(value = "files", required = false) MultipartFile[] files,
                                        HttpServletRequest request){
        return ResponseEntity.ok(postService.createPost(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                postRequestDTO,
                files
        ));
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFilePost(@PathVariable("id") String id,
                                            @RequestPart("type") Integer[] type,
                                            @RequestPart MultipartFile[] files){
        return ResponseEntity.ok(postService.uploadFilePost(id, files, type));
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

    @GetMapping(value = "/{id}/hidden")
    public ResponseEntity<?> hiddenPost(@PathVariable(name= "id") String id){
        return ResponseEntity.ok(postService.hiddenPost(id));
    }

    @PostMapping(value = "/share/{id}")
    public ResponseEntity<?> sharePost( @PathVariable(name = "id") String id,
                                        HttpServletRequest request){
        return ResponseEntity.ok(postService.sharePost(
                id,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @PostMapping(value = "/reaction/{id}")
    public ResponseEntity<?> reactionPost( @PathVariable(name = "id") String id,
                                           @RequestBody ReactionRequestDTO reactionRequestDTO,
                                           HttpServletRequest request){
        return ResponseEntity.ok(postService.reactionPost(
                id,
                reactionRequestDTO,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @PostMapping(value = "/un-reaction/{id}")
    public ResponseEntity<?> unReactionPost( @PathVariable(name = "id") String id,
                                             HttpServletRequest request){
        return ResponseEntity.ok(postService.unReactionPost(
                id,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }
}

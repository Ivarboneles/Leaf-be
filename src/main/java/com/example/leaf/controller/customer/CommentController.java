package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ChatRequestDTO;
import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.services.CommentService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<?> getAllCommentByPost(@PathVariable(name = "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentRequestDTO,
                                           HttpServletRequest request){
        return ResponseEntity.ok(commentService.createComment(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                commentRequestDTO
        ));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") String id ,
                                           @RequestBody String content){
        return ResponseEntity.ok(commentService.updateComment(id,content));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name= "id") String id){
        return ResponseEntity.ok(commentService.hideComment(id));
    }

    @PostMapping(value = "/rep/{id}")
    public ResponseEntity<?> replyComment( @PathVariable(name = "id") String id,
                                            @RequestBody CommentRequestDTO commentRequestDTO,
                                           HttpServletRequest request){
        return ResponseEntity.ok(commentService.replyComment(
                id,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                commentRequestDTO.getContent()
        ));
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

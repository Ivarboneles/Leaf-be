package com.example.leaf.controller.admin;

import com.example.leaf.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class CommentManagerController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "comment/disable/{id}")
    public ResponseEntity<?> disableComment(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(commentService.disableComment(id));
    }

    @PostMapping(value = "comment/enable/{username}")
    public ResponseEntity<?> enableComment(@PathVariable(value = "id")  String id){
        return ResponseEntity.ok(commentService.enableComment(id));
    }

    @GetMapping(value = "/all-comment/{page}")
    public ResponseEntity<?> getListCommentOfPage(@PathVariable(value = "page") Integer page){
        return ResponseEntity.ok(commentService.getListCommentOfPage(page));
    }
}

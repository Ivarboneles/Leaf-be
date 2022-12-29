package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.RelationShipRequestDTO;
import com.example.leaf.services.RelationShipService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/relationship")
public class RelationshipController {

    @Autowired
    RelationShipService relationShipService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @GetMapping(value = "/all/{username}")
    public ResponseEntity<?> getAllRelationShipByUser(@PathVariable(name = "username") String username){
        return ResponseEntity.ok(
                relationShipService.getAllRelationShipByUsername(username)
        );
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllRelationShipByCurrentUser(HttpServletRequest request){
        return ResponseEntity.ok(
                relationShipService.getAllRelationShipByUser(jwtTokenUtil.getUserDetails(
                        JwtTokenUtil.getAccessToken(request)
                ))
        );
    }

    @GetMapping(value = "/invitation")
    public ResponseEntity<?> getAllInvitationFriend(HttpServletRequest request){
        return ResponseEntity.ok(
                relationShipService.getAllInvitationFriend(jwtTokenUtil.getUserDetails(
                        JwtTokenUtil.getAccessToken(request)
                ))
        );
    }

    @GetMapping(value = "/friend")
    public ResponseEntity<?> getAllFriend(HttpServletRequest request){
        return ResponseEntity.ok(
                relationShipService.getAllFriend(jwtTokenUtil.getUserDetails(
                        JwtTokenUtil.getAccessToken(request)
                ))
        );
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getRelationShipByUsername(@PathVariable(name = "username") String username,
                                                       HttpServletRequest request){
        return ResponseEntity.ok(
                relationShipService.getRelationShipById(
                        jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                        username
                )
        );
    }

    @PostMapping(value = "/{username}")
    public ResponseEntity<?> createRelationShip(@PathVariable("username") String username, HttpServletRequest request){
        return ResponseEntity.ok(relationShipService.createRelationShip(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)), username));
    }

    @PutMapping
    public ResponseEntity<?> updateRelationShip(@RequestBody RelationShipRequestDTO relationShipRequestDTO, HttpServletRequest request){
        return ResponseEntity.ok(relationShipService.updateRelationShip(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                , relationShipRequestDTO.getUsernameTo(), relationShipRequestDTO.getStatus()));
    }

    @DeleteMapping(value = "/{friend}")
    public ResponseEntity<?> deleteRelationShip(@PathVariable(name= "friend") String friend,
                                                HttpServletRequest request){
        return ResponseEntity.ok(relationShipService.deleteRelationShip(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                friend
        ));
    }


}

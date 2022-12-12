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
    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getAllRelationShipByUser(@PathVariable(name = "username") String username){
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getRelationShipById(@RequestParam(name = "userFrom") String userFrom,
                                                 @RequestParam(name = "userTo") String userTo){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createRelationShip(@RequestBody String username, HttpServletRequest request){
        return ResponseEntity.ok(relationShipService.createRelationShip(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)), username));
    }

    @PutMapping
    public ResponseEntity<?> updateRelationShip(@RequestBody RelationShipRequestDTO relationShipRequestDTO, HttpServletRequest request){
        return ResponseEntity.ok(relationShipService.updateRelationShip(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                , relationShipRequestDTO.getUsernameTo(), relationShipRequestDTO.getStatus()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRelationShip(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }
}

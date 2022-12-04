package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.RelationShipRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/relationship")
public class RelationshipController {

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
    public ResponseEntity<?> createRelationShip(@RequestBody RelationShipRequestDTO relationShipRequestDTO){
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateRelationShip(@RequestBody RelationShipRequestDTO relationShipRequestDTO){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRelationShip(@PathVariable(name= "id") UUID id){
        return ResponseEntity.ok().build();
    }
}

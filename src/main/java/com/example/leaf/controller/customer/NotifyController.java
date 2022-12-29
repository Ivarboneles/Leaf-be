package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ListNotifyRequestDTO;
import com.example.leaf.dto.request.NotifyRequestDTO;
import com.example.leaf.services.NotifyService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/notify")
public class NotifyController {
    @Autowired
    NotifyService notifyService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @GetMapping(value = "/user")
    public ResponseEntity<?> getAllNotifyByUser(HttpServletRequest request){

        return ResponseEntity.ok(notifyService.getAllNotifyByUser(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @PostMapping
    public ResponseEntity<?> createNotify(@RequestBody NotifyRequestDTO notifyRequestDTO){
        return ResponseEntity.ok(notifyService.createNotify(
                notifyRequestDTO
        ));
    }

    @PostMapping(value = "/seen", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> seenNotify(@RequestBody ListNotifyRequestDTO listNotifyRequestDTO){

        return ResponseEntity.ok(notifyService.seenNotify(listNotifyRequestDTO.getIds()));
    }

}

package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.UserRequestDTO;
import com.example.leaf.dto.request.VerifyRequestDTO;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.User;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.UserService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserById (@PathVariable(name = "username") String username) {
        return ResponseEntity.ok( userService.getUserById(username));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.saveUser(userRequestDTO));
    }

    @PutMapping(value = "/update-profile/{username}")
    public ResponseEntity<?> updateProfile(@PathVariable(name = "username") String username,@RequestBody UserRequestDTO userRequestDTO){
        return  ResponseEntity.ok(userService.updateUser(username, userRequestDTO));
    }

    @PutMapping(value = "/change-password/{username}")
    public ResponseEntity<?> changePassword(@PathVariable(name = "username") String username,@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        return  ResponseEntity.ok(userService.changePassword(username, changePasswordRequestDTO));
    }

    @PostMapping(value = "/change-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeAvatar(
            @RequestPart(name = "avatar") MultipartFile avatar,
            HttpServletRequest request){

        return ResponseEntity.ok(userService.changeAvatar(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)), avatar));
    }

    @PostMapping(value = "/send-verify")
    public ResponseEntity<?> sendVerify(@RequestBody VerifyRequestDTO verifyRequestDTO){
        return null;
    }
}

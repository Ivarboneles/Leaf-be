package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.*;
import com.example.leaf.services.UserService;
import com.example.leaf.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        return ResponseEntity.ok(userService.saveUser(registerUserRequestDTO));
    }

    @PutMapping(value = "/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO,
                                           HttpServletRequest request){
        return  ResponseEntity.ok(userService.updateUser(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)), userUpdateRequestDTO));
    }

    @PutMapping(value = "/change-password")
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
    public ResponseEntity<?> sendVerify(@RequestBody String email){
        return ResponseEntity.ok( userService.sendVerifyCode(email));
    }

    @PostMapping(value = "/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyRequestDTO verifyRequestDTO){
        return ResponseEntity.ok(userService.verifyUser(verifyRequestDTO));
    }

    @PutMapping(value = "/change-email")
    public ResponseEntity<?> changeEmail(@RequestBody String email,
                                         HttpServletRequest request){
        return ResponseEntity.ok(userService.changeEmail(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),email));
    }

    @PutMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO,
                                            HttpServletRequest request){

        return ResponseEntity.ok(userService.forgotPassword(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)) , forgotPasswordRequestDTO));

    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getUserById(username));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<?> searchUser(@PathVariable("name") String name){
        return ResponseEntity.ok(userService.searchUser(name));
    }

    @GetMapping(value = "/list-friend")
    public ResponseEntity<?> getListFriend(HttpServletRequest request){
        return ResponseEntity.ok(userService.getListFriend(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @GetMapping(value = "/list-post")
    public ResponseEntity<?> getListPost(HttpServletRequest request){
        return ResponseEntity.ok(userService.getListPost(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }
}

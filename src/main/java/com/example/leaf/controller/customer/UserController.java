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
    public ResponseEntity<?> getUser(@PathVariable("username") String username,
                                     HttpServletRequest request){
        return ResponseEntity.ok(userService.getUserById(username,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                ));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<?> searchUser(@PathVariable("name") String name,
                                        HttpServletRequest request){
        return ResponseEntity.ok(userService.searchUser(name,
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
                ));
    }

    @GetMapping(value = "/search-friend/{name}")
    public ResponseEntity<?> searchFriend(@PathVariable("name") String name,
                                          HttpServletRequest request){
        return ResponseEntity.ok(userService.searchFriend(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                name
        ));
    }

    @GetMapping(value = "/recomend-friend")
    public ResponseEntity<?> getRecommendFriend(HttpServletRequest request){

        return ResponseEntity.ok(userService.getRecommendFriend(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }

    @GetMapping(value = "/list-friend/{size}")
    public ResponseEntity<?> getListFriend(@PathVariable(name = "size") Integer size,
                                           HttpServletRequest request){
        return ResponseEntity.ok(userService.getListFriendWithPage(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)),
                size
        ));
    }

    @GetMapping(value = "/count-friend")
    public ResponseEntity<?> getCountFriend(HttpServletRequest request){
        return ResponseEntity.ok(
                userService.countFriend(jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request)))
        );
    }

    @GetMapping(value = "/list-post")
    public ResponseEntity<?> getListPost(HttpServletRequest request){
        return ResponseEntity.ok(userService.getListPost(
                jwtTokenUtil.getUserDetails(JwtTokenUtil.getAccessToken(request))
        ));
    }


    @PostMapping(value = "/upload-file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(encoding = @Encoding(name = "storyRequestDTO", contentType = "application/json")))
    public ResponseEntity<?> uploadFileOfMessage(@RequestBody MultipartFile[] files){
        return ResponseEntity.ok(userService.uploadFileOfMessage(
                files
        ));
    }
}

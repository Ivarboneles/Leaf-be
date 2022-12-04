package com.example.leaf.controller.customer;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.UserRequestDTO;
import com.example.leaf.dto.request.VerifyRequestDTO;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserResponseDTO> getUserById (@PathVariable(name = "username") String username) {
        return userService.getUserById(username);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody UserRequestDTO userRequestDTO,
                                                     HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        String siteUrl = request.getRequestURL().toString().replace(request.getServletPath(), "");
        return userService.saveUser(userRequestDTO, siteUrl);
    }

    @PutMapping(value = "/update-profile/{username}")
    public ResponseEntity<ResponseObject> updateProfile(@PathVariable(name = "username") String username,@RequestBody UserRequestDTO userRequestDTO){
        return  userService.updateUser(username, userRequestDTO);
    }

    @PutMapping(value = "/change-password/{username}")
    public ResponseEntity<ResponseObject> changePassword(@PathVariable(name = "username") String username,@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        return  userService.changePassword(username, changePasswordRequestDTO);
    }

    @PostMapping(value = "/send-verify")
    public ResponseEntity<ResponseObject> sendVerify(@RequestBody VerifyRequestDTO verifyRequestDTO){
        return null;
    }

}

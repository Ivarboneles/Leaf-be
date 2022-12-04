package com.example.leaf.controller;

import com.example.leaf.dto.request.LoginRequestDTO;
import com.example.leaf.dto.response.LoginResponseDTO;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.entities.enums.RoleEnum;
import com.example.leaf.services.LoginService;
import com.example.leaf.services.UserService;
import com.example.leaf.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping(value = "/api/v1")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager auth;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Operation(
            summary = "Login API for customer",
            description = "Login key can be username, phone number or email that user is registered.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful",
                    content = { @Content(mediaType = "application/json") })
    })
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginRequestDTO body) throws LoginException {
        return ResponseEntity.ok(login(body, RoleEnum.CUSTOMER));
    }

    public LoginResponseDTO<?> login(LoginRequestDTO body, RoleEnum r) throws LoginException {
        return loginService.authenticateWithUsernamePassword(body.getLoginKey(), body.getPassword(), r);
    }


}

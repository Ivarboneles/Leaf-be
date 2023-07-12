package com.example.leaf.controller;

import com.example.leaf.dto.request.LoginRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.enums.RoleEnum;
import com.example.leaf.repositories.UserRepository;
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
    UserRepository userRepository;

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
    @PostMapping(value = "/login/customer")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequestDTO body) throws LoginException {

        return ResponseEntity.ok(login(body, RoleEnum.CUSTOMER));
    }

    @Operation(
            summary = "Login API for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful",
                    content = { @Content(mediaType = "application/json") })
    })
    @PostMapping(value = "/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequestDTO body) throws LoginException {
        return ResponseEntity.ok(login(body, RoleEnum.ADMIN));
    }

    public DataResponse<?> login(LoginRequestDTO body, RoleEnum r) throws LoginException {
        return loginService.authenticateWithUsernamePassword(body.getLoginKey(), body.getPassword(), r);
    }


}

package com.example.leaf.services;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.LoginResponseDTO;
import com.example.leaf.entities.enums.RoleEnum;

import javax.security.auth.login.LoginException;

public interface LoginService {
    DataResponse<?> authenticateWithUsernamePassword(String username, String password, RoleEnum requestedRole) throws LoginException;
}

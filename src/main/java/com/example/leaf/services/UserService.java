package com.example.leaf.services;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.ForgotPasswordRequestDTO;
import com.example.leaf.dto.request.RegisterUserRequestDTO;
import com.example.leaf.dto.request.UserUpdateRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    DataResponse<?> saveUser(RegisterUserRequestDTO registerUserRequestDTO);
    DataResponse<?> updateUser(User user, UserUpdateRequestDTO userUpdateRequestDTO);
    DataResponse<?> changePassword(String username, ChangePasswordRequestDTO changePasswordRequestDTO);
    DataResponse<?> getUserById(String username);
    DataResponse<?> verifyUser(String verifyCode);

    DataResponse<?> sendVerifyCode(String email);

    DataResponse<?> changeAvatar(User user, MultipartFile avatar);

    DataResponse<?> changeEmail(User user, String email);

    DataResponse<?> forgotPassword(User user, ForgotPasswordRequestDTO forgotPasswordRequestDTO);

}



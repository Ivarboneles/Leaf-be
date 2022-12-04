package com.example.leaf.services;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.UserRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.User;
import com.example.leaf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    DataResponse<?> saveUser(UserRequestDTO userRequestDTO, String siteUrl)
            throws MessagingException, UnsupportedEncodingException;
    DataResponse<?> updateUser(String username, UserRequestDTO userRequestDTO);
    DataResponse<?> changePassword(String username, ChangePasswordRequestDTO changePasswordRequestDTO);
    DataResponse<?> getUserById(String username);
    DataResponse<?> verifyUser(String verifyCode);
}



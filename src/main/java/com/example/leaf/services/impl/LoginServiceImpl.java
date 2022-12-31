package com.example.leaf.services.impl;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.LoginResponseDTO;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.RoleEnum;

import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.LoginService;
import com.example.leaf.utils.JwtTokenUtil;

import com.example.leaf.utils.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.security.auth.login.LoginException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    ModelMapper mapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    ServiceUtils serviceUtils;

    @Override
    public DataResponse<?> authenticateWithUsernamePassword(String username, String password, RoleEnum requestedRole) throws LoginException {
        //check user
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        @SuppressWarnings("rawtypes")
        User user = (User) auth.getPrincipal();
        //create token
        String token = generateToken(user.getUsername());

        String r = user.getRole().getName();
        //check role
        if (requestedRole.equals(RoleEnum.CUSTOMER) && requestedRole.toString().equals(r)) { // buyer
            return new DataResponse<>(new LoginResponseDTO<>(
                    token,
                    mapper.map(user, UserResponseDTO.class))
            );
        }
        else if (requestedRole.equals(RoleEnum.ADMIN) && !r.equals(RoleEnum.CUSTOMER.toString())) {
            return new DataResponse<>(new LoginResponseDTO<>(
                    token,
                    mapper.map(user, UserResponseDTO.class))
            );
        }
        else {
            throw new LoginException("Username or password is invalid for this system");
        }
    }

    public String generateToken(String username) {
        Optional<User> user =userRepository.findUserByUsername(username);
        if(user.isPresent()) {
            return jwtUtil.generateToken(user.get());
        }

        return null;
    }
}

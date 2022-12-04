package com.example.leaf.services.impl;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.UserRequestDTO;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.Role;
import com.example.leaf.entities.User;
import com.example.leaf.exceptions.ResourceAlreadyExistsException;
import com.example.leaf.exceptions.ResourceNotFoundException;

import com.example.leaf.repositories.RoleRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.UserService;
import net.bytebuddy.utility.RandomString;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JavaMailSender mailSender;


    @Override
    public ResponseEntity<ResponseObject> saveUser(UserRequestDTO userRequestDTO, String siteUrl)
            throws MessagingException, UnsupportedEncodingException {
        User user =  mapper.map(userRequestDTO, User.class);

        // Check phone user existed
        Optional<User> userCheckPhone = userRepository.findUserByPhone(userRequestDTO.getPhone());
        if (userCheckPhone.isPresent()) {
            throw new ResourceAlreadyExistsException("Phone user existed");
        }

        // Check email user existed
        Optional<User> userCheckEmail = userRepository.findUserByEmail(userRequestDTO.getEmail());
        if (userCheckEmail.isPresent()) {
            throw new ResourceAlreadyExistsException("Email user existed");
        }

        encodePassword(user);
        // Check role already exists
        Role role = roleRepository.findRoleByName(user.getRole().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find role with ID = " + user.getRole().getName()));
        user.setRole(role);
        user.setEnable(false);

        String randomCodeVerify = RandomString.make(64);
        user.setVerificationCode(randomCodeVerify);

        UserResponseDTO userResponseDTO = mapper.map(userRepository.save(user), UserResponseDTO.class);
        sendVerificationEmail(user, siteUrl);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create user successfully!", userResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateUser(String username, UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        User userExists = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + username));

        // Check email user existed
        if (!user.getEmail().equals(userExists.getEmail())) {
            Optional<User> userCheckEmail = userRepository.findUserByEmail(userRequestDTO.getEmail());
            if (userCheckEmail.isPresent()) {
                throw new ResourceAlreadyExistsException("Email user existed");
            }
        }

        // Check phone user existed
        if (!user.getPhone().equals(userExists.getPhone())) {
            Optional<User> userCheckPhone = userRepository.findUserByPhone(userRequestDTO.getPhone());
            if (userCheckPhone.isPresent()) {
                throw new ResourceAlreadyExistsException("Phone user existed");
            }
        }

        //Update password
        if (userRequestDTO.getPassword() == null){
            user.setPassword(userExists.getPassword());
        } else {
            encodePassword(user);
        }

        //Update enable
        if (userRequestDTO.getEnable() == null){
            user.setEnable(userExists.isEnabled());
        } else {
            user.setEnable(userRequestDTO.getEnable());
        }

        // Check role already exists
        Role role = roleRepository.findRoleByName(user.getRole().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find role with ID = " + user.getRole().getName()));
        user.setRole(role);
        UserResponseDTO userResponseDTO = mapper.map(userRepository.save(user), UserResponseDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update user successfully!",
                userResponseDTO));

    }

    @Override
    public ResponseEntity<ResponseObject> changePassword(String username, ChangePasswordRequestDTO changePasswordRequestDTO) {
        User userExists = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + username));
        String oldPassword = changePasswordRequestDTO.getOldPassword();
        String newPassword = changePasswordRequestDTO.getNewPassword();
        String verification = changePasswordRequestDTO.getVerificationCode();

        if(userExists.getVerificationCode().equals(verification)) {
            if(userExists.getPassword().equals(oldPassword)){
                userExists.setPassword(newPassword);
                encodePassword(userExists);
            }else {
                throw new ResourceNotFoundException("Old password no match");
            }

        }else {
            throw new ResourceNotFoundException("Verify failed!");
        }
        UserResponseDTO userResponseDTO = mapper.map(userRepository.save(userExists), UserResponseDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update user successfully!",
                userResponseDTO));
    }

    @Override
    public ResponseEntity<UserResponseDTO> getUserById(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + username));
        UserResponseDTO userResponseDTO = mapper.map(user, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @Override
    public ResponseEntity<ResponseObject> verifyUser(String verifyCode) {
        User getUser = userRepository.findUserByVerificationCode(verifyCode)
                .orElseThrow(() -> new ResourceNotFoundException("Verify code is incorrect"));
        getUser.setEnable(true);
        User user = userRepository.save(getUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Verify account success!!!"));
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void sendVerificationEmail(User user, String siteUrl)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "Mobile University Store";
        String verifyUrl = siteUrl + "/verify?code=" + user.getVerificationCode();
        String mailContent = "<p>Dear " + user.getName() + ",<p><br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href = \"" + verifyUrl + "\">VERIFY</a></h3>"
                + "Thank you,<br>" + "Mobile University.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom("mobileuniversity@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}

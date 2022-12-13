package com.example.leaf.services.impl;

import com.example.leaf.dto.request.ChangePasswordRequestDTO;
import com.example.leaf.dto.request.ForgotPasswordRequestDTO;
import com.example.leaf.dto.request.RegisterUserRequestDTO;
import com.example.leaf.dto.request.UserUpdateRequestDTO;
import com.example.leaf.dto.response.*;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.GenderEnum;
import com.example.leaf.entities.enums.RoleEnum;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.exceptions.ResourceAlreadyExistsException;
import com.example.leaf.exceptions.ResourceNotFoundException;

import com.example.leaf.repositories.PostRepository;
import com.example.leaf.repositories.RelationShipRepository;
import com.example.leaf.repositories.RoleRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.UserService;
import com.example.leaf.utils.JwtTokenUtil;
import com.example.leaf.utils.ServiceUtils;
import net.bytebuddy.utility.RandomString;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    ImageService imageService;
    @Autowired
    ServiceUtils serviceUtils;

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

    @Autowired
    RelationShipRepository relationShipRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Override
    public DataResponse<?> saveUser(RegisterUserRequestDTO registerUserRequestDTO) {
        User user =  mapper.map(registerUserRequestDTO, User.class);

        // Check phone user existed
        Optional<User> userCheckUsername = userRepository.findUserByUsername(registerUserRequestDTO.getUsername());
        if (userCheckUsername.isPresent()) {
            throw new ResourceAlreadyExistsException("Username user existed");
        }

        // Check phone user existed
        Optional<User> userCheckPhone = userRepository.findUserByPhone(registerUserRequestDTO.getPhone());
        if (userCheckPhone.isPresent()) {
            throw new ResourceAlreadyExistsException("Phone user existed");
        }

        // Check email user existed
        Optional<User> userCheckEmail = userRepository.findUserByEmail(registerUserRequestDTO.getEmail());
        if (userCheckEmail.isPresent()) {
            throw new ResourceAlreadyExistsException("Email user existed");
        }

        encodePassword(user);
        // Check role already exists
        user.setRole(roleRepository.findRoleByName(RoleEnum.CUSTOMER.toString()).get());
        user.setEnable(true);
        user.setGender(GenderEnum.MALE.toString());
        user.setAvatar("https://storage.googleapis.com/leaf-5c2c4.appspot.com/39f94986-d898-49dd-b9eb-5ff979857ab9png");

        String randomCodeVerify = RandomString.make(64);
        user.setVerificationCode(randomCodeVerify);
        return serviceUtils.convertToDataResponse(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public DataResponse<?> updateUser(User user, UserUpdateRequestDTO userUpdateRequestDTO) {

        if(userUpdateRequestDTO.getPhone() != null){
            if(!user.getPhone().equals(userUpdateRequestDTO.getPhone())){
                Optional<User> userOptional = userRepository.findUserByPhone(userUpdateRequestDTO.getPhone());
                if(userOptional.isPresent()){
                    throw new ResourceNotFoundException("Phone has already!");
                }
                user.setPhone(userUpdateRequestDTO.getPhone());
            }
        }

        if (userUpdateRequestDTO.getName() != null){
            if(!user.getName().equals(userUpdateRequestDTO.getName())){
                user.setName(userUpdateRequestDTO.getName());
            }
        }

        if(userUpdateRequestDTO.getBirthday() != null){
            if(user.getBirthday() != userUpdateRequestDTO.getBirthday()){
                user.setBirthday(userUpdateRequestDTO.getBirthday());
            }
        }

        if(userUpdateRequestDTO.getBio() != null){
            user.setBio(userUpdateRequestDTO.getBio());
        }

       if(userUpdateRequestDTO.getGender() != null){
           if(!user.getGender().equals(userUpdateRequestDTO.getGender())){
               user.setGender(userUpdateRequestDTO.getGender());
           }
       }

       if(userUpdateRequestDTO.getNickname() != null){
           user.setNickname(userUpdateRequestDTO.getNickname());
       }

       String token = jwtTokenUtil.generateToken(user);

       return new DataResponse<>( new LoginResponseDTO<>(token, mapper.map( userRepository.save(user), UserResponseDTO.class )));

    }

    @Override
    public DataResponse<?> changePassword(String username, ChangePasswordRequestDTO changePasswordRequestDTO) {
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

        return serviceUtils.convertToDataResponse(userRepository.save(userExists), UserResponseDTO.class);
    }

    @Override
    public DataResponse<?> getUserById(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + username));

        return serviceUtils.convertToDataResponse(user, UserResponseDTO.class);
    }

    @Override
    public DataResponse<?> verifyUser(String verifyCode) {
        User getUser = userRepository.findUserByVerificationCode(verifyCode)
                .orElseThrow(() -> new ResourceNotFoundException("Verify code is incorrect"));
        getUser.setEnable(true);

        return serviceUtils.convertToDataResponse(userRepository.save(getUser), UserResponseDTO.class);
    }

    @Override
    public DataResponse<?> sendVerifyCode(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user " + email));
        try{
            sendVerificationEmail(user, email);
        }catch (Exception e){
            throw new InvalidValueException("Can't not connect to your email");
        }

        DataResponse dataResponse = new DataResponse();
        dataResponse.setMessage("The verify code is sent your email!");
        return dataResponse;
    }

    @Override
    public DataResponse<?> changeAvatar(User user, MultipartFile avatar) {
        try{
            String fileName = imageService.save(avatar);

            String imageUrl = imageService.getImageUrl(fileName);

            user.setAvatar(imageUrl);

            return serviceUtils.convertToDataResponse(userRepository.save(user), UserResponseDTO.class);

        }catch (Exception e){
            throw new InvalidValueException("Can't upload file");
        }
    }

    @Override
    public DataResponse<?> changeEmail(User user, String email) {
        if(email != null){
            if(!user.getEmail().equals(email)){
                Optional<User> userOptional = userRepository.findUserByEmail(email);

                if(userOptional.isPresent()){
                    throw new ResourceNotFoundException("Email has already");
                }
                user.setEmail(email);
            }
        }
        return serviceUtils.convertToDataResponse(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public DataResponse<?> forgotPassword(User user, ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
       if(user.getVerificationCode().equals(forgotPasswordRequestDTO.getVerifyCode())){
           if(forgotPasswordRequestDTO.getNewPassword() != null){
               user.setPassword(forgotPasswordRequestDTO.getNewPassword());
               encodePassword(user);
           }
       }else{
           throw new InvalidValueException("Verify email failed!");
       }

        return serviceUtils.convertToDataResponse(userRepository.save(user), UserResponseDTO.class);

    }

    @Override
    public ListResponse<?> searchUser(String name) {
        return serviceUtils.convertToListResponse(userRepository.searchByName(name), SearchUserResponseDTO.class) ;
    }

    @Override
    public ListResponse<?> getListFriend(User user) {
        return serviceUtils.convertToListResponse(relationShipRepository.findAllByUserFromOrUserTo(user,user), FriendResponseDTO.class);
    }

    @Override
    public ListResponse<?> getListPost(User user) {
        return serviceUtils.convertToListResponse(postRepository.findAllByUserAndStatus(user, StatusEnum.ENABLE.toString()), PostOfUserResponseDTO.class);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void sendVerificationEmail(User user, String siteUrl)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "Leaf App";
        String verifyUrl = siteUrl + "/verify?code=" + user.getVerificationCode();
        String mailContent = "<p>Dear " + user.getName() + ",<p><br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href = \"" + verifyUrl + "\">VERIFY</a></h3>"
                + "Thank you,<br>" + "Leaf App";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom("leafapp@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}

package com.example.leaf.mapper;

import com.example.leaf.dto.request.UserRequestDTO;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "sping")
public interface UserMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "phone", source = "dto.phone")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "birthday", source = "dto.birthday")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "nickname", source = "dto.nickname")
    @Mapping(target = "bio", source = "dto.bio")
    @Mapping(target = "avatar", expression = "java(null)")
    @Mapping(target = "role.id", source = "dto.role")
    @Mapping(target = "enable", source = "dto.enable")
    User userRequestDTOtoUser(UserRequestDTO dto);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "address", source = "user.address")
    @Mapping(target = "birthday", source = "user.birthday")
    @Mapping(target = "gender", source = "user.gender")
    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "bio", source = "user.bio")
    @Mapping(target = "avatar", source = "user.avatar")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "enable", source = "user.enable")
    UserResponseDTO userToUserResponseDTO(User user);
}

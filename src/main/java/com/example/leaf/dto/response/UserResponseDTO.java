package com.example.leaf.dto.response;

import com.example.leaf.entities.Address;
import com.example.leaf.entities.Role;
import com.example.leaf.entities.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String username;
    private String name;
    private String phone;
    private String email;
    private Address address;
    private String birthday;
    private String genderEnum;
    private String nickname;
    private String bio;
    private Boolean enable;
    private String avatar;
    private Role role;

}

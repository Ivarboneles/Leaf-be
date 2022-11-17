package com.example.leaf.dto.response;

import com.example.leaf.entities.Role;
import com.example.leaf.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String phone;
    private String email;
    private String address;
    private String birthday;
    private Gender gender;
    private String nickname;
    private String bio;
    private Boolean enable;
    private String image;
    private Role role;

}

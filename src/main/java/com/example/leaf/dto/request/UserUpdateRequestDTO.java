package com.example.leaf.dto.request;

import com.example.leaf.entities.Address;
import com.example.leaf.entities.Role;
import com.example.leaf.entities.enums.GenderEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDTO {

    private String name;
    @Size(max = 12, min = 9)
    private String phone;
    private Date birthday;
    private String gender;
    private String nickname;
    private String bio;
    private String security;

}

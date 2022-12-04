package com.example.leaf.dto.request;

import com.example.leaf.entities.Address;
import com.example.leaf.entities.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String name;
    private String username;
    private String password;
    @NotNull(message="An email is required!")
    @Size(message="Invalid size.", max = 30, min=10)
    @Pattern(regexp=("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$"), message = "Invalid email")
    private String email;
    @Size(message="Invalid size.", max = 10, min=10)
    @Pattern(regexp=("^0\\d{9}$"), message = "Invalid phone")
    private String phone;
    private String birthday;
    private GenderEnum genderEnum;
    private Boolean enable;

}

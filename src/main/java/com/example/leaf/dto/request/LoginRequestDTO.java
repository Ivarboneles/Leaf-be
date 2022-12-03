package com.example.leaf.dto.request;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginRequestDTO {
    @NotEmpty
    private String loginKey;
    @NotEmpty
    private String password;
}

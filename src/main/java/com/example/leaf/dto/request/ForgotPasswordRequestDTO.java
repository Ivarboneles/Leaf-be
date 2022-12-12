package com.example.leaf.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordRequestDTO {
    @NotNull
    private String newPassword;
    @NotNull
    private String verifyCode;
}

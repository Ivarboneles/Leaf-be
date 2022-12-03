package com.example.leaf.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class VerifyRequestDTO {

    @NotNull
    String username;
    @NotNull
    String email;
}

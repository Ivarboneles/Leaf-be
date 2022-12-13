package com.example.leaf.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchUserResponseDTO {
    private String username;
    private String name;
    private String avatar;
}

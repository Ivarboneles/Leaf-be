package com.example.leaf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NotifyResponseDTO {

    private String id;
    private SearchUserResponseDTO user;
    private Date createDate;
    private String value;
    private String status;
}

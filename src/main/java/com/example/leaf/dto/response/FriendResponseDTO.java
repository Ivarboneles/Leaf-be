package com.example.leaf.dto.response;

import com.example.leaf.entities.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FriendResponseDTO {
    private SearchUserResponseDTO userFrom;
    private SearchUserResponseDTO userTo;
    private Date createDate;
    private String status;
}

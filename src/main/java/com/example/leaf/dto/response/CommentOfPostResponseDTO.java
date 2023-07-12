package com.example.leaf.dto.response;

import com.example.leaf.entities.Comment;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentOfPostResponseDTO {
    private String id;
    private SearchUserResponseDTO user;
    private String value;
    private Integer type;
    private Date createDate;
    private String status;
    private Integer countRep = 0;
    private List<ReactionOfCommentResponseDTO> reactions;
}

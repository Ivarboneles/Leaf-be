package com.example.leaf.dto.response;

import com.example.leaf.entities.Reaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReactionCommentResponseDTO {
    private CommentOfReactionResponseDTO comment;
    private Reaction reaction;
    private SearchUserResponseDTO user;
    private Date createDate;
    private String status;
}

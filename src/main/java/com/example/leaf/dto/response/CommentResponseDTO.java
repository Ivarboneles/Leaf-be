package com.example.leaf.dto.response;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.entities.Comment;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDTO {

    private String id;
    private SearchUserResponseDTO user;

    private PostOfCommentResponseDTO post;

    private CommentFatherResponseDTO comment;
    private String value;

    private Integer type;

    private Date createDate;

    private String status;
}

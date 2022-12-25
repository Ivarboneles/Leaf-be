package com.example.leaf.dto.response;

import com.example.leaf.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDTO {
    private String id;
    private SearchUserResponseDTO user;
    private PostFatherResponseDTO post;
    private Date createDate;
    private String value;
    private String status;
    private List<CommentOfPostResponseDTO> comments;
    private List<FileOfPostResponseDTO> files;
    private List<ReactionPostResponseDTO> reactions;
}

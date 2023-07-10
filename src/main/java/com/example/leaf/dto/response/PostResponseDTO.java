package com.example.leaf.dto.response;

import com.example.leaf.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private String id;
    private SearchUserResponseDTO user;
    private PostFatherResponseDTO post;
    private Date createDate;
    private String value;
    private String status;
    private String security;
    private Boolean likedPost = false;
    private Integer countComment = 0;
    private Integer countReaction = 0;
    private List<FileOfPostResponseDTO> files;
    private List<CommentOfPostResponseDTO> comments;
}

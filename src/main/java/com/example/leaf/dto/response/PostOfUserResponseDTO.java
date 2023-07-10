package com.example.leaf.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostOfUserResponseDTO {
    private String id;
    private PostFatherResponseDTO post;
    private Date createDate;
    private String value;
    private Integer countComment;
    private String security;
    private Integer countReaction;
    private Boolean likedPost = false;
    private List<FileOfPostResponseDTO> files;
    private List<CommentOfPostResponseDTO> comments;
}

package com.example.leaf.services;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;

public interface CommentService {
    DataResponse<?> createComment(User user, CommentRequestDTO commentRequestDTO);

    DataResponse<?> updateComment(String id, String content);

    DataResponse<?> hideComment(String id);

    DataResponse<?> replyComment(String commentId, User user, String content);

    DataResponse<?> reactionComment(String commentId, ReactionRequestDTO reactionRequestDTO, User user);

    DataResponse<?> unReactionComment(String commentId, User user);

    ListResponse<?> getAllCommentByPostAndPageSize(String postId, Integer size);

    ListResponse<?> getAllCommentByFather(String fatherId);
}

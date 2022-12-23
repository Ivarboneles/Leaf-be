package com.example.leaf.services;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.User;

public interface CommentService {
    DataResponse<?> createComment(User user, CommentRequestDTO commentRequestDTO);

    DataResponse<?> updateComment(String id, String content);

    DataResponse<?> hideComment(String id);

    DataResponse<?> replyComment(String commentId, User user, String content);
}

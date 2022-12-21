package com.example.leaf.services.impl;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.response.CommentResponseDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.Comment;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.CommentRepository;
import com.example.leaf.repositories.PostRepository;
import com.example.leaf.services.CommentService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ServiceUtils serviceUtils;

    @Override
    public DataResponse<?> createComment(User user, CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setId(serviceUtils.GenerateID());
        comment.setCreateDate(new Date());
        comment.setStatus(StatusEnum.ENABLE.toString());
        comment.setUser(user);
        comment.setType(1);
        comment.setValue(commentRequestDTO.getContent());
        comment.setPost(postRepository.findById(commentRequestDTO.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Can't find post")
        ));
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> updateComment(String id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("can't find comment")
        );
        comment.setValue(content);
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> hideComment(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("can't find comment")
        );
        comment.setStatus(StatusEnum.DISABLE.toString());
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }
}

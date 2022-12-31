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
        //Create new Comment
        Comment comment = new Comment();
        comment.setId(serviceUtils.GenerateID());
        comment.setCreateDate(new Date());
        comment.setStatus(StatusEnum.ENABLE.toString());
        comment.setUser(user);
        comment.setType(1);
        comment.setValue(commentRequestDTO.getContent());
        //find Post
        comment.setPost(postRepository.findById(commentRequestDTO.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Can't find post")
        ));
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> updateComment(String id, String content) {
        //Check comment
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("can't find comment")
        );
        //Change content comment
        comment.setValue(content);
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> hideComment(String id) {
        //Check comment
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("can't find comment")
        );
        //Change status comment
        comment.setStatus(StatusEnum.DISABLE.toString());
        return serviceUtils.convertToDataResponse(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> replyComment(String commentId, User user, String content) {
        //Create new comment
        Comment newComment = new Comment();
        //Set father comment
        Comment fatherComment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("can't find comment " + commentId) );
        newComment.setComment(fatherComment);
        newComment.setId(serviceUtils.GenerateID());
        newComment.setStatus(StatusEnum.ENABLE.toString());
        newComment.setValue(content);
        newComment.setType(1);
        newComment.setCreateDate(new Date());
        newComment.setUser(user);
        newComment.setPost(fatherComment.getPost());
        return serviceUtils.convertToDataResponse(commentRepository.save(newComment) , CommentResponseDTO.class);
    }
}

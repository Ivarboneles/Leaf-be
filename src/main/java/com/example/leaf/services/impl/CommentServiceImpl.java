package com.example.leaf.services.impl;

import com.example.leaf.dto.request.CommentRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.response.*;
import com.example.leaf.entities.*;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.ReactionCommentKey;
import com.example.leaf.entities.keys.ReactionPostKey;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.CommentRepository;
import com.example.leaf.repositories.PostRepository;
import com.example.leaf.repositories.ReactionCommentRepository;
import com.example.leaf.repositories.ReactionRepository;
import com.example.leaf.services.CommentService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    ReactionCommentRepository reactionCommentRepository;

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

    @Override
    public DataResponse<?> reactionComment(String commentId, ReactionRequestDTO reactionRequestDTO, User user) {
        Optional<ReactionComment> reactionCommentOptional = reactionCommentRepository.findById(new ReactionCommentKey(commentId, user.getUsername()));
        ReactionComment reactionComment = new ReactionComment();

        if(reactionCommentOptional.isPresent()) {
            reactionComment = reactionCommentOptional.get();
        } else {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new ResourceNotFoundException("can't found comment " + commentId)
            );
            reactionComment.setComment(comment);
            reactionComment.setUser(user);
            reactionComment.setCreateDate( new Date());
        }

        Reaction reaction = reactionRepository.findById(reactionRequestDTO.getName()).orElseThrow(
                () -> new ResourceNotFoundException("can't found reaction " + reactionRequestDTO.getName())
        );

        reactionComment.setReaction(reaction);
        reactionComment.setStatus(StatusEnum.ENABLE.toString());

        return serviceUtils.convertToDataResponse(reactionCommentRepository.save(reactionComment), ReactionCommentResponseDTO.class);
    }

    @Override
    public DataResponse<?> unReactionComment(String commentId, User user) {
        System.out.println("CommentID : " + commentId);
        System.out.println("User : " + user.getUsername());
        ReactionComment reactionComment = reactionCommentRepository.findById(
                new ReactionCommentKey(commentId, user.getUsername())
        ).orElseThrow(
                () -> new ResourceNotFoundException("can't found reaction of comment " + commentId)
        );

        reactionComment.setStatus(StatusEnum.DISABLE.toString());
        return serviceUtils.convertToDataResponse(reactionCommentRepository.save(reactionComment), ReactionCommentResponseDTO.class);
    }

    @Override
    public ListResponse<?> getAllCommentByPostAndPageSize(String postId, Integer size) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Can't find post"));

        List<Comment> commentList = commentRepository.findAllByPostAndStatus(
                post,
                StatusEnum.ENABLE.toString(),
                PageRequest.of(size-1, 10).withSort(Sort.by("createDate").descending())
        ).getContent();
        return serviceUtils.convertToListResponse(commentList, CommentOfPostResponseDTO.class);
    }
}

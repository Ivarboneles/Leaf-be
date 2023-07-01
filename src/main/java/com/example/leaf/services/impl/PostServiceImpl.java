package com.example.leaf.services.impl;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.PostResponseDTO;
import com.example.leaf.dto.response.ReactionPostResponseDTO;
import com.example.leaf.entities.*;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.ReactionPostKey;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.*;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.PostService;
import com.example.leaf.utils.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    ServiceUtils serviceUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    ReactionPostRepository reactionPostRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    ModelMapper mapper;

    @Autowired
    ImageService imageService;
    @Override
    public DataResponse<?> createPost(User user, PostRequestDTO postRequestDTO, MultipartFile[] files) {
        //Create new post
        Post post = new Post();
        post.setValue(postRequestDTO.getValue());
        post.setId(serviceUtils.GenerateID());
        post.setCreateDate(new Date());
        post.setUser(user);
        post.setStatus(StatusEnum.ENABLE.toString());
        post.setComments(new ArrayList<>());
        post.setFiles(new ArrayList<>());
        post.setReactions(new ArrayList<>());
        postRepository.save(post);
        if(files != null) {
            //upload post's file
            uploadFile(files, post, postRequestDTO.getType());
        }
        return serviceUtils.convertToDataResponse(postRepository.save(post), PostResponseDTO.class);
    }

    @Override
    public DataResponse<?> uploadFilePost(String postId, MultipartFile[] files, Integer[] types) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Can't found post")
        );
        uploadFile(files, post,types );
        return serviceUtils.convertToDataResponse(post, PostResponseDTO.class);
    }

    @Override
    public DataResponse<?> updatePost(String id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Can't find post")
        );

        if(postRequestDTO.getValue() != null){
            post.setValue(postRequestDTO.getValue());
        }
        return serviceUtils.convertToDataResponse(postRepository.save(post), PostResponseDTO.class);
    }

    @Override
    public DataResponse<?> deletePost(String id) {
        DataResponse dataResponse = new DataResponse<>();
        dataResponse.setMessage("Delete post successful");
        try{
            postRepository.deleteById(id);

        }catch (Exception e){
            throw new InvalidValueException("Can't delete post " + e.getMessage());
        }
        return dataResponse;
    }

    @Override
    public DataResponse<?> hiddenPost(String id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Can't find post")
        );
        //set status -> Disable
        post.setStatus(StatusEnum.DISABLE.toString());
        return serviceUtils.convertToDataResponse(postRepository.save(post), PostResponseDTO.class);
    }

    @Override
    public ListResponse<?> getListPostOfUser(User user) {
        List<Post> list =postRepository.findAllByUserAndStatus(user, StatusEnum.ENABLE.toString(),Sort.by("createDate").descending());

        return serviceUtils.convertToListResponse(
                list,
                PostResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getAllPostOfUser(User user) {
        List<Post> list = postRepository.findAllByUser(user, Sort.by("createDate").descending());

        return serviceUtils.convertToListResponse(
                list,
                PostResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getNewFeedPost(Integer page) {
        List<Post> list = postRepository.findAllByStatus(StatusEnum.ENABLE.toString(),PageRequest.of(page-1, 15).withSort(Sort.by("createDate").descending())).getContent();
        return serviceUtils.convertToListResponse(
                list,
                PostResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getListPostOfUserName(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Can't found user " + username)
        );
        return serviceUtils.convertToListResponse(
                postRepository.findAllByUserAndStatus(user, StatusEnum.ENABLE.toString(), Sort.by("createDate").descending()),
                PostResponseDTO.class
        );
    }

    @Override
    public DataResponse<?> sharePost(String postId, User user) {
        Post newPost = new Post();
        newPost.setId(serviceUtils.GenerateID());
        newPost.setValue("");
        newPost.setStatus(StatusEnum.ENABLE.toString());
        newPost.setUser(user);
        newPost.setCreateDate(new Date());
        newPost.setPost(postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("can't found post " + postId)
        ));
        newPost.setFiles(new ArrayList<>());
        newPost.setComments(new ArrayList<>());
        return serviceUtils.convertToDataResponse(postRepository.save(newPost), PostResponseDTO.class);
    }

    @Override
    public DataResponse<?> reactionPost(String postId, ReactionRequestDTO reactionRequestDTO, User user) {
        Optional<ReactionPost> reactionPostOptional = reactionPostRepository.findById(new ReactionPostKey(postId, user.getUsername()));
        ReactionPost reactionPost = new ReactionPost();

        if(reactionPostOptional.isPresent()) {
            reactionPost = reactionPostOptional.get();
        } else {
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new ResourceNotFoundException("can't found post " + postId)
            );
            reactionPost.setPost(post);
            reactionPost.setUser(user);
            reactionPost.setCreateDate( new Date());
        }

        Reaction reaction = reactionRepository.findById(reactionRequestDTO.getName()).orElseThrow(
                () -> new ResourceNotFoundException("can't found reaction " + reactionRequestDTO.getName())
        );

        reactionPost.setReaction(reaction);
        reactionPost.setStatus(StatusEnum.ENABLE.toString());

        return serviceUtils.convertToDataResponse(reactionPostRepository.save(reactionPost), ReactionPostResponseDTO.class);
    }

    @Override
    public DataResponse<?> unReactionPost(String postId, User user) {
        ReactionPost reactionPost = reactionPostRepository.findById(
                new ReactionPostKey(postId, user.getUsername())
        ).orElseThrow(
                () -> new ResourceNotFoundException("can't found reaction of post " + postId)
        );

        reactionPost.setStatus(StatusEnum.DISABLE.toString());
        return serviceUtils.convertToDataResponse(reactionPostRepository.save(reactionPost), ReactionPostResponseDTO.class);
    }


    void uploadFile(MultipartFile[] files, Post post, Integer[] type){
        try{
            //read file in files
            for(int i = 0; i < files.length ; i ++){
                //Upload file
                String fileName = imageService.save(files[i]);
                String imageUrl = imageService.getImageUrl(fileName);

                //Create file in db
                File newFile = new File();
                newFile.setId(serviceUtils.GenerateID());
                newFile.setPost(post);
                newFile.setValue(imageUrl);
                //read type of file
                if(type != null) {
                    newFile.setType(type[i]);
                }else {
                    newFile.setType(1);
                }
                newFile.setStatus(StatusEnum.ENABLE.toString());
                //Add file to files of post
                post.getFiles().add(fileRepository.save(newFile));
            }
        }catch (Exception e){
            throw new InvalidValueException("Can't upload file : " + e.getMessage());
        }
    }
}



package com.example.leaf.services.impl;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.PostResponseDTO;
import com.example.leaf.entities.File;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.FileRepository;
import com.example.leaf.repositories.PostRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.PostService;
import com.example.leaf.utils.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

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
    FileRepository fileRepository;
    @Autowired
    ModelMapper mapper;

    @Autowired
    ImageService imageService;
    @Override
    public DataResponse<?> createPost(User user, PostRequestDTO postRequestDTO, MultipartFile[] files) {
        Post post = new Post();
        try{
            post.setValue(postRequestDTO.getValue());
            post.setId(serviceUtils.GenerateID());
            post.setCreateDate(new Date());
            post.setUser(user);
            post.setStatus(StatusEnum.ENABLE.toString());
            post.setComments(new ArrayList<>());
            post.setFiles(new ArrayList<>());
            postRepository.save(post);
            for(MultipartFile file : files){
                String fileName = imageService.save(file);

                String imageUrl = imageService.getImageUrl(fileName);

                File newFile = new File();
                newFile.setId(serviceUtils.GenerateID());
                newFile.setPost(post);
                newFile.setValue(imageUrl);
                newFile.setType(1);
                newFile.setStatus(StatusEnum.ENABLE.toString());
                post.getFiles().add(fileRepository.save(newFile));
            }
        }catch (Exception e){
            throw new InvalidValueException("Can't upload file : " + e.getMessage());
        }
        return serviceUtils.convertToDataResponse(postRepository.save(post), PostResponseDTO.class);
    }

    @Override
    public DataResponse<?> uploadFilePost(String postId, MultipartFile[] files) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Can't found post")
        );
        try{
            for(MultipartFile file : files){
                String fileName = imageService.save(file);

                String imageUrl = imageService.getImageUrl(fileName);

                File newFile = new File();
                newFile.setId(serviceUtils.GenerateID());
                newFile.setPost(post);
                newFile.setValue(imageUrl);
                newFile.setType(1);
                newFile.setStatus(StatusEnum.ENABLE.toString());
                post.getFiles().add(fileRepository.save(newFile));
            }
        }catch (Exception e){
            throw new InvalidValueException("Can't upload file : " + e.getMessage());
        }
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
    public ListResponse<?> getListPostOfUser(User user) {

        return serviceUtils.convertToListResponse(
                postRepository.findAllByUserAndStatus(user, StatusEnum.ENABLE.toString()),
                PostResponseDTO.class
        );
    }
}

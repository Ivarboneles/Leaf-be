package com.example.leaf.services;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    DataResponse<?> createPost(User user, PostRequestDTO postRequestDTO, MultipartFile[] files);

    DataResponse<?> uploadFilePost(String postId, MultipartFile[] files);

    DataResponse<?> updatePost(String id, PostRequestDTO postRequestDTO);

    DataResponse<?> deletePost(String id);

    ListResponse<?> getListPostOfUser(User user);
}

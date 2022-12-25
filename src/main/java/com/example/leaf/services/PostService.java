package com.example.leaf.services;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    DataResponse<?> createPost(User user, PostRequestDTO postRequestDTO, MultipartFile[] files);

    DataResponse<?> uploadFilePost(String postId, MultipartFile[] files, Integer[] types);

    DataResponse<?> updatePost(String id, PostRequestDTO postRequestDTO);

    DataResponse<?> deletePost(String id);
    DataResponse<?> hiddenPost(String id);

    ListResponse<?> getListPostOfUser(User user);

    ListResponse<?> getAllPostOfUser(User user);

    ListResponse<?> getNewFeedPost(Integer page);

    ListResponse<?> getListPostOfUserName(String username);

    DataResponse<?> sharePost(String postId, User user);
}

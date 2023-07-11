package com.example.leaf.services;

import com.example.leaf.dto.request.PostRequestDTO;
import com.example.leaf.dto.request.ReactionRequestDTO;
import com.example.leaf.dto.request.UpdatePostRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    DataResponse<?> createPost(User user, PostRequestDTO postRequestDTO, MultipartFile[] files);

    DataResponse<?> uploadFilePost(String postId, MultipartFile[] files, Integer[] types);

    DataResponse<?> updatePost(String id, UpdatePostRequestDTO postRequestDTO);

    DataResponse<?> deletePost(String id);
    DataResponse<?> hiddenPost(String id);
    DataResponse<?> changeSecurityPost(String postId, String security, User user);
    ListResponse<?> getListPostOfUser(User user);

    ListResponse<?> getAllPostOfUser(User user);

    ListResponse<?> getNewFeedPost(Integer page, User user);

    ListResponse<?> getListPostOfUserName(String username, User user);

    ListResponse<?> getListReactionByPostAndPageSize(String postId, Integer size);

    DataResponse<?> sharePost(String postId, User user);

    DataResponse<?> reactionPost(String postId, ReactionRequestDTO reactionRequestDTO, User user);

    DataResponse<?> unReactionPost(String postId, User user);
}

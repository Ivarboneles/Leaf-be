package com.example.leaf.services;

import com.example.leaf.dto.request.StoryRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.Story;
import com.example.leaf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StoryService {
    DataResponse<?> createStory(User user, MultipartFile[] files);
    ListResponse<?> getAllStoryByUser(User user);

    DataResponse<?> getStoryById(String id);
    ListResponse<?> getListStory(User user);
    DataResponse<?> deletetStory(String id);
}

package com.example.leaf.services.impl;

import com.example.leaf.dto.request.StoryRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.StoryResponseDTO;
import com.example.leaf.entities.File;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.Story;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.repositories.FileRepository;
import com.example.leaf.repositories.StoryRepository;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.StoryService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class StoryServiceImpl implements StoryService {
    @Autowired
    ImageService imageService;
    @Autowired
    FileRepository fileRepository;

    @Autowired
    StoryRepository storyRepository;
    @Autowired
    ServiceUtils serviceUtils;
    @Override
    public DataResponse<?> createStory(StoryRequestDTO requestDTO, User user, MultipartFile[] files) {
        Story story = new Story();
        story.setCreateDate( new Date());
        story.setUser(user);
        story.setStatus(StatusEnum.ENABLE.toString());
        story.setId(serviceUtils.GenerateID());
        if(files[0] != null){
            story.setValue(uploadFile(files[0]));
        }
        if(files[1] != null){
            story.setMusic(uploadFile(files[0]));
        }
        return serviceUtils.convertToDataResponse(storyRepository.save(story), StoryResponseDTO.class);
    }

    @Override
    public ListResponse<?> getListStory(User user) {
        return null;
    }

    @Override
    public DataResponse<?> deletetStory(String id) {
        return null;
    }

    String uploadFile(MultipartFile file){
        try{
            //read file in files
           if(file != null){
               //Upload file
               String fileName = imageService.save(file);
               String imageUrl = imageService.getImageUrl(fileName);

               return fileName;
           }

           return  null;
        }catch (Exception e){
            throw new InvalidValueException("Can't upload file : " + e.getMessage());
        }
    }
}

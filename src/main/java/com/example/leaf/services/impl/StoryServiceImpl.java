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
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.FileRepository;
import com.example.leaf.repositories.StoryRepository;
import com.example.leaf.services.ImageService;
import com.example.leaf.services.StoryService;
import com.example.leaf.utils.ServiceUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public DataResponse<?> createStory(User user, MultipartFile[] files) {
        Story story = new Story();
        story.setCreateDate( new Date());
        story.setUser(user);
        story.setStatus(StatusEnum.ENABLE.toString());
        story.setId(serviceUtils.GenerateID());
        for(int i = 0; i < files.length; i++){
            if(i == 0) {
                if (files[i] != null) {
                    story.setValue(uploadFile(files[0]));
                }
            }else if(i == 1){
                if(files[i] != null){
                    story.setMusic(uploadFile(files[0]));
                }
            }
        }
        return serviceUtils.convertToDataResponse(storyRepository.save(story), StoryResponseDTO.class);
    }

    @Override
    public ListResponse<?> getAllStoryByUser(User user) {
        List<Story> storyList = storyRepository.findAllByUserAndStatus(user, StatusEnum.ENABLE.toString());
        return serviceUtils.convertToListResponse(storyList, StoryResponseDTO.class);
    }

    @Override
    public DataResponse<?> getStoryById(String id) {
        Story story = storyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Can't find story")
        );

        return serviceUtils.convertToDataResponse(storyRepository.save(story), StoryResponseDTO.class);
    }

    @Override
    public ListResponse<?> getListStory(User user) {
        List<Story> listResult = new ArrayList<>();
        try {
            List<User> list = serviceUtils.recommendUser(user);
            List<Story> stories = storyRepository.findAllByUserAndStatus(
                    user,
                    StatusEnum.ENABLE.toString(),
                    PageRequest.of(0, 1).withSort(Sort.by("createDate").descending())
            ).getContent();
            if(!stories.isEmpty()){
                listResult.add(stories.get(0));
            }
            for(User item : list){
                List<Story> listStory = storyRepository.findAllByUserAndStatus(
                        item,
                        StatusEnum.ENABLE.toString(),
                        PageRequest.of(0, 1).withSort(Sort.by("createDate").descending())
                ).getContent();

                if(!listStory.isEmpty()) {
                    listResult.add(listStory.get(0));
                }

                if(listResult.size() > 7){
                    break;
                }
            }
            return serviceUtils.convertToListResponse(listResult, StoryResponseDTO.class);
        } catch (TasteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataResponse<?> deletetStory(String id) {
        Story story = storyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Can't find story")
        );

        story.setStatus(StatusEnum.DISABLE.toString());

        return serviceUtils.convertToDataResponse(storyRepository.save(story), StoryResponseDTO.class);
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

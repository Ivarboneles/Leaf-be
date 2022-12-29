package com.example.leaf.services.impl;

import com.example.leaf.dto.request.NotifyRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.NotifyResponseDTO;
import com.example.leaf.entities.Notify;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.NotifyRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.NotifyService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    ServiceUtils serviceUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotifyRepository notifyRepository;

    @Override
    public ListResponse<?> getAllNotifyByUser(User user) {
        return serviceUtils.convertToListResponse(notifyRepository.findAllByUser(user, Sort.by("createDate").descending()), NotifyResponseDTO.class);
    }

    @Override
    public DataResponse<?> createNotify(NotifyRequestDTO notifyRequestDTO) {
        Notify notify = new Notify(
                serviceUtils.GenerateID(),
                userRepository.findUserByUsername(notifyRequestDTO.getUsername()).orElseThrow(
                        () -> new ResourceNotFoundException("Can't find user "+notifyRequestDTO.getUsername())
                ),
                new Date(),
                notifyRequestDTO.getContent(),
                StatusEnum.WAITING.toString()
        );
        return serviceUtils.convertToDataResponse(
                notifyRepository.save(notify),
                NotifyResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> seenNotify(String[] ids) {
        List<Notify> list = new ArrayList<>();
        for(String id : ids){
            Notify notify = notifyRepository.getReferenceById(id);
            notify.setStatus(StatusEnum.SEEN.toString());
            list.add(notifyRepository.save(notify));
        }
        return serviceUtils.convertToListResponse(list, NotifyResponseDTO.class);
    }
}

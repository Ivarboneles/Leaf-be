package com.example.leaf.services.impl;


import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.RelationShipResponseDTO;
import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.RelationEnum;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.RelationShipKey;
import com.example.leaf.exceptions.InvalidValueException;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.RelationShipRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.RelationShipService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RelationShipServiceImpl implements RelationShipService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationShipRepository relationShipRepository;

    @Autowired
    ServiceUtils serviceUtils;

    @Override
    public ListResponse<?> getAllRelationShipByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Can't find user " + username)
        );

        return serviceUtils.convertToListResponse(
                relationShipRepository.findAllByUserFromOrUserTo(user, user),
                RelationShipResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getAllRelationShipByUser(User user) {
        return serviceUtils.convertToListResponse(
                relationShipRepository.findAllByUserFromOrUserTo(user, user),
                RelationShipResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getAllInvitationFriend(User user) {
        return serviceUtils.convertToListResponse(
                relationShipRepository.findAllByUserToAndStatus(user, RelationEnum.WAITING.toString()),
                RelationShipResponseDTO.class
        );
    }

    @Override
    public ListResponse<?> getAllFriend(User user) {
        List<RelationShip> list = relationShipRepository.findAllByUserFromOrUserTo(user,user);
        List<RelationShip> result = new ArrayList<>();
        for(RelationShip relationShip: list){
            if(relationShip.getStatus().equals( RelationEnum.FOLLOW.toString())){
                result.add(relationShip);
            }
        }
        return serviceUtils.convertToListResponse(
                result,
                RelationShipResponseDTO.class
        );
    }

    @Override
    public DataResponse<?> getRelationShipById(User user, String username) {
        Optional<RelationShip> relationShip =  relationShipRepository.findById(new RelationShipKey(user.getUsername(), username));
        if(relationShip.isPresent()){
           return serviceUtils.convertToDataResponse(relationShip.get(), RelationShipResponseDTO.class);
        }
        relationShip =  relationShipRepository.findById(new RelationShipKey(username, user.getUsername()));
        if(relationShip.isPresent()){
            return serviceUtils.convertToDataResponse(relationShip.get(), RelationShipResponseDTO.class);
        }
        return new DataResponse<>();
    }

    @Override
    public DataResponse createRelationShip(User userFrom, String usernameTo) {
        User userTo = userRepository.findUserByUsername(usernameTo)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user " + usernameTo));

        RelationShip relationShip = new RelationShip();
        relationShip.setUserFrom(userFrom);
        relationShip.setUserTo(userTo);
        relationShip.setStatus(RelationEnum.WAITING.toString());
        return serviceUtils.convertToDataResponse(relationShipRepository.save(relationShip), RelationShipResponseDTO.class) ;
    }

    @Override
    public DataResponse<?> updateRelationShip(User userFrom, String usernameTo, String status) {
        User userTo = userRepository.findUserByUsername(usernameTo)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user " + usernameTo));
        RelationShip relationShip = relationShipRepository.
                findById(new RelationShipKey(userFrom.getUsername(), usernameTo)).
                orElseThrow(() ->
                        new ResourceNotFoundException("Can't found relationship of user "+
                                userFrom.getUsername() + " and user " + usernameTo));
        relationShip.setStatus(status);
        return serviceUtils.convertToDataResponse(relationShipRepository.save(relationShip), RelationShipResponseDTO.class) ;
    }

    @Override
    public DataResponse<?> deleteRelationShip(User user, String friend) {
        User userTo = userRepository.findUserByUsername(friend)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user " + friend));
        try{
            relationShipRepository.deleteById(new RelationShipKey(user.getUsername(), friend));
        }catch (Exception e){
            throw new InvalidValueException("Can't delete relationship!");
        }
        DataResponse dataResponse = new DataResponse<>();
        dataResponse.setMessage("Delete Relationship successful!");
        return dataResponse;
    }
}

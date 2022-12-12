package com.example.leaf.services.impl;


import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.RelationShipKey;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.repositories.RelationShipRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.services.RelationShipService;
import com.example.leaf.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RelationShipServiceImpl implements RelationShipService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationShipRepository relationShipRepository;

    @Override
    public DataResponse createRelationShip(User userFrom, String usernameTo) {
        User userTo = userRepository.findUserByUsername(usernameTo)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user " + usernameTo));

        RelationShip relationShip = new RelationShip();
        relationShip.setUserFrom(userFrom);
        relationShip.setUserTo(userTo);
        relationShip.setStatusEnum(StatusEnum.FOLLOWING);
        return new DataResponse<>(relationShipRepository.save(relationShip)) ;
    }

    @Override
    public DataResponse<?> updateRelationShip(User userFrom, String usernameTo, StatusEnum status) {
        User userTo = userRepository.findUserByUsername(usernameTo)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user " + usernameTo));
        RelationShip relationShip = relationShipRepository.
                findById(new RelationShipKey(userFrom.getUsername(), usernameTo)).
                orElseThrow(() ->
                        new ResourceNotFoundException("Can't found relationship of user "+
                                userFrom.getUsername() + " and user " + usernameTo));
        relationShip.setStatusEnum(status);
        return new DataResponse<>(relationShipRepository.save(relationShip));
    }
}

package com.example.leaf.services;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;

public interface RelationShipService {

    ListResponse<?> getAllRelationShipByUsername(String username);
    ListResponse<?> getAllRelationShipByUser(User user);
    ListResponse<?> getAllInvitationFriend(User user);
    ListResponse<?> getAllFriend(User user);

    DataResponse<?> getRelationShipById(User user, String username );

    DataResponse<?> createRelationShip(User userFrom, String usernameTo);

    DataResponse<?> updateRelationShip(User userFrom, String usernameTo, String status);

    DataResponse<?> deleteRelationShip(User user, String friend);
}

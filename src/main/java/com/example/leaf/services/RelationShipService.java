package com.example.leaf.services;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;

public interface RelationShipService {

    DataResponse<?> createRelationShip(User userFrom, String usernameTo);

    DataResponse<?> updateRelationShip(User userFrom, String usernameTo, String status);

    DataResponse<?> deleteRelationShip(User user, String friend);
}

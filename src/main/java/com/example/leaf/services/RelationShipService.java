package com.example.leaf.services;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;

public interface RelationShipService {

    DataResponse<?> createRelationShip(User userFrom, String usernameTo);

    DataResponse<?> updateRelationShip(User userFrom, String usernameTo, StatusEnum status);
}

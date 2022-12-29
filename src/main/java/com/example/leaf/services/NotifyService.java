package com.example.leaf.services;

import com.example.leaf.dto.request.NotifyRequestDTO;
import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.User;

public interface NotifyService {
    ListResponse<?> getAllNotifyByUser(User user);

    DataResponse<?> createNotify(NotifyRequestDTO notifyRequestDTO);
    ListResponse<?> seenNotify(String[] ids);


}

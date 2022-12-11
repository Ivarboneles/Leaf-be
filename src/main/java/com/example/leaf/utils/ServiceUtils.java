package com.example.leaf.utils;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceUtils {
    @Autowired
    ModelMapper mapper;

    public <T, V> ListResponse<V> convertToListResponse(List<T> src, Class<V> cls) {
        return new ListResponse<>(src.stream().map(p -> mapper.map(p, cls)).toList());
    }
    public <T, V> DataResponse<V> convertToDataResponse(T src, Class<V> cls) {
        return new DataResponse<>(mapper.map(src, cls));
    }

    public String GenerateID (){
        return UUID.randomUUID().toString();
    }

}

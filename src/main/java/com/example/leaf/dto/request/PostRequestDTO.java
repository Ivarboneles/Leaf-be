package com.example.leaf.dto.request;

import com.example.leaf.entities.Comment;
import com.example.leaf.entities.Post;
import com.example.leaf.entities.User;
import com.example.leaf.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    @NotNull(message = "Value is required")
    private String value;
    private String security;
    private Integer[] type;
}

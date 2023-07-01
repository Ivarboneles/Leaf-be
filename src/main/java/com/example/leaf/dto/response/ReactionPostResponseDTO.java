package com.example.leaf.dto.response;

import com.example.leaf.entities.Post;
import com.example.leaf.entities.Reaction;
import com.example.leaf.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReactionPostResponseDTO {

    private Reaction reaction;
    private SearchUserResponseDTO user;
    private Date createDate;
    private String status;
}

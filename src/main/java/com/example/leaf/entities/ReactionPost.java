package com.example.leaf.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.ReactionPostKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reaction_post")
@IdClass(ReactionPostKey.class)
public class ReactionPost {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "post")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reaction")
    private Reaction reaction;

    @NotNull(message = "Create date is required")
    @CreationTimestamp
    private Date createDate;


    @NotNull(message = "Status is required")
    private String status;
}

package com.example.leaf.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.leaf.entities.enums.Status;
import com.example.leaf.entities.keys.ReactionPostKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "reaction")
    private Reaction reaction;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @NotNull(message = "Create date is required")
    @Temporal(TemporalType.DATE)
    private Date createDate;


    @NotNull(message = "Status is required")
    private Status status;
}

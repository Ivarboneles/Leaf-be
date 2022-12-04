package com.example.leaf.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.ReactionCommentKey;
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
@Table(name = "reaction_comment")
@IdClass(ReactionCommentKey.class)
public class ReactionComment {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "reaction")
    private Reaction reaction;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    @NotNull(message = "Create date is required")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @NotNull(message = "Status is required")
    private StatusEnum statusEnum;

}

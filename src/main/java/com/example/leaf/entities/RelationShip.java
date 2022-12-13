package com.example.leaf.entities;

import com.example.leaf.entities.enums.StatusEnum;
import com.example.leaf.entities.keys.RelationShipKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "relationship")
@IdClass(RelationShipKey.class)
public class RelationShip {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_from")
    private User userFrom;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_to")
    private User userTo;

    @CreationTimestamp
    private Date createDate;

    @NotNull(message = "Status is required")
    private String status;

}

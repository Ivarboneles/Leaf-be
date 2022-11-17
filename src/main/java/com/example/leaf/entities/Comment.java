package com.example.leaf.entities;

import com.example.leaf.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Comment comment;

    @NotNull(message = "Value is required")
    private String value;

    @NotNull(message = "Type is required")
    private Integer type;

    @NotNull(message = "Create date is required")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @NotNull(message = "Status is required")
    private Status status;
}

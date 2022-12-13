package com.example.leaf.entities;

import com.example.leaf.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Post post;

    @NotNull(message = "Create date is required")
    @CreationTimestamp
    private Date createDate;
    @NotNull(message = "Value is required")
    private String value;

    @NotNull(message = "Status is required")
    private String status;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<File> files;
}

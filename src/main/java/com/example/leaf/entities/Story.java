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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story")
public class Story {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Story story;

    @NotNull(message = "Create date is required")
    @CreationTimestamp
    private Date createDate;

    @NotNull(message = "Value is required")
    private String value;

    private String music;

    @NotNull(message = "Status is required")
    private String status;
}

package com.example.leaf.entities;


import com.example.leaf.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;

    @NotNull(message = "Value is required")
    private String value;

    @NotNull(message = "Type is required")
    private Integer type;

    @NotNull(message = "Status is required")
    private String status;
}

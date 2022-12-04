package com.example.leaf.entities;
import com.example.leaf.entities.enums.ReactEnum;
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
@Table(name = "reaction")
public class Reaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(length = 16)
    private UUID id;

    @NotNull(message = "Reaction name is required")
    private ReactEnum name;

    @NotNull(message = "Status is required")
    private StatusEnum statusEnum;
}

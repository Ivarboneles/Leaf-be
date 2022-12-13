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
    private String name;

    @NotNull(message = "Status is required")
    private String status;
}

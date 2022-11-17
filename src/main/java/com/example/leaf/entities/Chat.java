package com.example.leaf.entities;

import com.example.leaf.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_from", referencedColumnName = "id")
    private User userFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_to", referencedColumnName = "id")
    private User userTo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Chat chat;

    @NotNull(message = "Create date is required")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @NotNull(message = "Value is required")
    private String value;

    @NotNull(message = "Type is required")
    private Integer type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reaction_id", referencedColumnName = "id")
    private Reaction reaction;

    @NotNull(message = "Status is required")
    private Status status;
}

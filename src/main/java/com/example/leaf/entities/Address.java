package com.example.leaf.entities;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.example.leaf.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    private String id;

    @NotNull(message = "Address city is required")
    private String country;

    @NotNull(message = "Address province is required")
    private String province;

    @NotNull(message = "Address district is required")
    private String district;

    @NotNull(message = "Address ward is required")
    private String ward;

    @NotNull(message = "Address detail is required")
    private String addressDetail;

    @NotNull(message = "Status is required")
    private String status;
}

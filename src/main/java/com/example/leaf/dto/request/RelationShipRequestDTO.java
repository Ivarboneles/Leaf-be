package com.example.leaf.dto.request;

import com.example.leaf.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationShipRequestDTO {
    @NotNull
    private String usernameTo;
    @NotNull
    private StatusEnum status;
}

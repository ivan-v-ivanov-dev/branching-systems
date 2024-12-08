package com.personal.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TeamGatewayRq {

    @NotBlank(message = "Team name can't be blank")
    @NotEmpty(message = "Team name can't be empty")
    private String name;

    @Positive(message = "Project id must be positive number")
    private int projectId;

    @Positive(message = "Leader id must be positive number")
    private int leaderId;
}

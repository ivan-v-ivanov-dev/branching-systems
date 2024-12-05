package com.personal.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class TeamRq {

    @NotBlank(message = "Team name can't be blank")
    @NotEmpty(message = "Team name can't be empty")
    private String name;

    @Positive(message = "Project id must be positive number")
    private int projectId;

    @Positive(message = "Leader id must be positive number")
    private int leaderId;
}

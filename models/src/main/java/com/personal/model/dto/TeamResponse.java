package com.personal.model.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TeamResponse {

    private String name;

    private Set<ProjectResponse> projects;

    private UserResponse leader;

    private Set<UserResponse> members;

}

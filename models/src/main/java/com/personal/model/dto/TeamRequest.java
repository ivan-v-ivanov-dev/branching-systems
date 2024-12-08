package com.personal.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TeamRequest {

    private String name;

    private int projectId;

    private int leaderId;
}

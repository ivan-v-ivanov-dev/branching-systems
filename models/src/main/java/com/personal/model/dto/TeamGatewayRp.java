package com.personal.model.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TeamGatewayRp {

    private String name;

    private Set<ProjectGatewayRp> projects;

    private UserGatewayRp leader;

    private Set<UserGatewayRp> members;
}

package com.personal.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleGatewayRp {

    private String name;

    private int userCount;
}

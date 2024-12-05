package com.personal.model.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UserResponse {

    private String username;

    private String firstName;

    private String lastName;

    private String role;

    private int team;
}

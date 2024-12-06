package com.personal.project.model;

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
@Builder
public class UserRq {

    @NotEmpty(message = "Username can't be empty")
    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotEmpty(message = "First name can't be empty")
    @NotBlank(message = "First name can't be blank")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty")
    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @NotEmpty(message = "Role can't be empty")
    @NotBlank(message = "Role can't be blank")
    private String role;

    @Positive(message = "team must be positive number")
    private int team;
}

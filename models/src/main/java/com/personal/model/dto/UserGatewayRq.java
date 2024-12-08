package com.personal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UserGatewayRq {

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

}

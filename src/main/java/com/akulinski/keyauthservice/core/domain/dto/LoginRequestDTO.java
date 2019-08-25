package com.akulinski.keyauthservice.core.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}

package com.akulinski.keyauthservice.core.domain.dto;

import com.akulinski.keyauthservice.core.domain.UserType;
import lombok.Data;

@Data
public class AddUserDTO {
    private String username;
    private String applicationName;
    private UserType userType;
}

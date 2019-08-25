package com.akulinski.keyauthservice.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO {
    private Boolean success;
    private String message;

}

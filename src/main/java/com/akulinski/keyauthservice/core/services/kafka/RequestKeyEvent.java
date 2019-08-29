package com.akulinski.keyauthservice.core.services.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestKeyEvent implements Serializable {
    private String username;
    private String applicationName;
}
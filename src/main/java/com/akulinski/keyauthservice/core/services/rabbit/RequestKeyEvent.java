package com.akulinski.keyauthservice.core.services.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestKeyEvent implements Serializable {

    @JsonProperty
    private String username;

    @JsonProperty
    private String applicationName;
}

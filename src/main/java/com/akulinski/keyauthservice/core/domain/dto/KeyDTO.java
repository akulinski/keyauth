package com.akulinski.keyauthservice.core.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class KeyDTO implements Serializable {

    private String ident;

    private String keyValue;
}

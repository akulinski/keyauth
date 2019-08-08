package com.akulinski.keyauthservice.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class KeyDTO implements Serializable {

    private String ident;

    private String keyValue;
}

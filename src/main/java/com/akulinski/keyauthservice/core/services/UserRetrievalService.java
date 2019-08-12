package com.akulinski.keyauthservice.core.services;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRetrievalService {

    private final KeycloakRestTemplate keycloakRestTemplate;

    private String keycloakServerUrl;

    public UserRetrievalService(KeycloakRestTemplate keycloakRestTemplate, @Value("${keycloak.auth-server-url}") String keycloakServerUrl) {
        this.keycloakRestTemplate = keycloakRestTemplate;
        this.keycloakServerUrl = keycloakServerUrl;
    }

    public List<SecurityProperties.User> getUsers() {
        ResponseEntity<SecurityProperties.User[]> response = keycloakRestTemplate.getForEntity(URI.create(keycloakServerUrl + "/admin/realms/keyauthkeyclock/users"), SecurityProperties.User[].class);
        return Arrays.asList(response.getBody());
    }

}

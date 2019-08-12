package com.akulinski.keyauthservice.utils;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public class KeyCloakUtils {

    public static KeycloakAuthenticationToken convertRequestToPrincipal(HttpServletRequest httpServletRequest) {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) httpServletRequest.getUserPrincipal();
        principal.getPrincipal();
        return principal;
    }
}

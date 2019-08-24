package com.akulinski.keyauthservice.core.controllers.rest;

import com.akulinski.keyauthservice.core.domain.AppUser;
import com.akulinski.keyauthservice.core.domain.dto.AddUserDTO;
import com.akulinski.keyauthservice.core.services.AppUserService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final AppUserService appUserService;

    public ApplicationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity getAllAppsWithUsers(){
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/get-user-apps")
    public ResponseEntity getUserApps(KeycloakPrincipal principal) {

        final var username = principal.getName();

        final AppUser appUser = appUserService.getAppUser(username);

        return ResponseEntity.ok(appUser.getApplications());
    }

    @PostMapping("/add-user-to-application")
    public ResponseEntity addUserToApplication(AddUserDTO addUserDTO) {
        appUserService.addUser(addUserDTO);

        return ResponseEntity.ok(addUserDTO);
    }

}

package com.akulinski.keyauthservice.core.controllers.rest;

import com.akulinski.keyauthservice.core.services.UserRetrievalService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserRetrievalService userRetrievalService;

    public UserController(UserRetrievalService userRetrievalService) {
        this.userRetrievalService = userRetrievalService;
    }

    @GetMapping("users/get-all-users")
    @PreAuthorize("hasRole('VIEW_USERS')")
    public ResponseEntity<List<SecurityProperties.User>> getAllUsers() {
        return ResponseEntity.ok(userRetrievalService.getUsers());
    }
}

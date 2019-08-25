package com.akulinski.keyauthservice.core.controllers;

import com.akulinski.keyauthservice.config.JwtTokenProvider;
import com.akulinski.keyauthservice.core.domain.Role;
import com.akulinski.keyauthservice.core.domain.RoleType;
import com.akulinski.keyauthservice.core.domain.User;
import com.akulinski.keyauthservice.core.domain.dto.ApiResponseDTO;
import com.akulinski.keyauthservice.core.domain.dto.JwtAuthenticationResponseDTO;
import com.akulinski.keyauthservice.core.domain.dto.LoginRequestDTO;
import com.akulinski.keyauthservice.core.domain.dto.SignUpRequestDTO;
import com.akulinski.keyauthservice.core.domain.exceptions.AppException;
import com.akulinski.keyauthservice.core.repositories.RoleRepository;
import com.akulinski.keyauthservice.core.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsernameOrEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponseDTO(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (userRepository.findByUsernameOrEmail(signUpRequestDTO.getUsername(),signUpRequestDTO.getUsername()).isPresent()) {
            return new ResponseEntity(new ApiResponseDTO(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequestDTO.getName(), signUpRequestDTO.getUsername(),
                signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByUserAndRoleType(user, RoleType.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDTO(true, "User registered successfully"));
    }
}

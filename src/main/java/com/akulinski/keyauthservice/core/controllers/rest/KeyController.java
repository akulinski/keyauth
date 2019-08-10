package com.akulinski.keyauthservice.core.controllers.rest;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.KeyDTO;
import com.akulinski.keyauthservice.core.services.KeyService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class KeyController {

    private final KeyService keyService;

    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping("/keys")
    public ResponseEntity getAllKeys() {
        return ResponseEntity.ok(keyService.findAll());
    }

    @PostMapping("/keys")
    public ResponseEntity addKey(@RequestBody KeyDTO keyDTO) {

        final Key key = keyService.addKeyFromDTO(keyDTO);
        if (key != null) {
            return ResponseEntity.ok(key);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/redeem")
    public ResponseEntity redeem(@RequestBody KeyDTO keyDTO) {
        final Boolean redeem = keyService.redeem(keyDTO);
        if (redeem) {
            return ResponseEntity.ok(keyDTO);
        } else {
            return ResponseEntity.badRequest().body(keyDTO);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity validate(@RequestBody KeyDTO keyDTO) {
        final Boolean validateRequest = keyService.validateRequest(keyDTO);

        if (validateRequest) {
            return ResponseEntity.ok(keyDTO);
        }

        return ResponseEntity.badRequest().body(keyDTO);
    }

}

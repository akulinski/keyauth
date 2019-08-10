package com.akulinski.keyauthservice.core.controllers.forntend;

import com.akulinski.keyauthservice.core.services.KeyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class Index {

    private final KeyService keyService;

    public Index(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }


    @GetMapping(path = "/keys")
    public String customers(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        model.addAttribute("keysData", keyService.findAll().toArray());
        return "keys";
    }

}

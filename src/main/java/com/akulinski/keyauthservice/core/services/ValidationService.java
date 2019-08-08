package com.akulinski.keyauthservice.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    @Value("${config.key.pattern}")
    private String patternString;

    private Pattern pattern;

    @PostConstruct
    public void init() {
        pattern = Pattern.compile(patternString);
    }

    public Boolean validateRegex(String key) {
        final var matcher = pattern.matcher(key);
        return matcher.matches();
    }
}

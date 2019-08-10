package com.akulinski.keyauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KeyauthserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyauthserviceApplication.class, args);
    }

}

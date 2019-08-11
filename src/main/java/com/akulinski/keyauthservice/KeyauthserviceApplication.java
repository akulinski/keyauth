package com.akulinski.keyauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories("com.akulinski.keyauthservice.core.repositories.redis")
public class KeyauthserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyauthserviceApplication.class, args);
    }

}

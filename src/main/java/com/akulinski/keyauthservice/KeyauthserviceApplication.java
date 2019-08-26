package com.akulinski.keyauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class KeyauthserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyauthserviceApplication.class, args);
    }

}

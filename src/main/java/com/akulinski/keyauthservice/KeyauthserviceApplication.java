package com.akulinski.keyauthservice;

import com.akulinski.keyauthservice.core.services.kafka.RequestKeyEvent;
import com.akulinski.keyauthservice.core.services.kafka.SerialKeyEventProducer;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class KeyauthserviceApplication {

    @Autowired
    private SerialKeyEventProducer serialKeyEventProducer;

    private Faker faker = new Faker();

    public static void main(String[] args) {
        SpringApplication.run(KeyauthserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadKafka() {

        for (int i = 0; i < 100; i++) {
            RequestKeyEvent requestKeyEvent = new RequestKeyEvent();
            requestKeyEvent.setApplicationName(faker.app().name());
            requestKeyEvent.setUsername(faker.funnyName().name());
            serialKeyEventProducer.sendMessage(requestKeyEvent);
        }

    }

}

package com.akulinski.keyauthservice.core.services.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SerialKeyEventProducer {

    private static String KEY_REQUESTS_QUEUE = "key-requests";

    @Autowired
    private RabbitTemplate queueSender;

    public SerialKeyEventProducer(RabbitTemplate queueSender) {
        this.queueSender = queueSender;
    }

    public void sendMessage(RequestKeyEvent requestKeyEvent) {
        queueSender.convertAndSend(KEY_REQUESTS_QUEUE, requestKeyEvent);
        System.out.println("Sent Message ");
    }

}

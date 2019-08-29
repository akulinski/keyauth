package com.akulinski.keyauthservice.core.services.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class SerialKeyEventProducer {

    private static String KEY_REQUESTS_TOPIC = "key-requests";

    private final KafkaTemplate<String, RequestKeyEvent> kafkaTemplate;

    public SerialKeyEventProducer(KafkaTemplate<String, RequestKeyEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RequestKeyEvent requestKeyEvent) {

        ListenableFuture<SendResult<String, RequestKeyEvent>> future = kafkaTemplate
                .send(KEY_REQUESTS_TOPIC,requestKeyEvent.getUsername(),requestKeyEvent);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, RequestKeyEvent> result) {
                log.debug("Request Sent");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getLocalizedMessage());
            }
        });

    }

}

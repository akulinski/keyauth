package com.akulinski.keyauthservice.config;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import com.akulinski.keyauthservice.core.repositories.redis.KeyRedisRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.stream.Stream;

@Configuration
@Profile("dev")
@Slf4j
public class DataMockConfig {
    private Faker faker;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final KeyRepository keyRepository;

    @Autowired
    private KeyRedisRepository keyRedisRepository;

    public DataMockConfig(KeyRepository keyRepository, RedisTemplate<Object, Object> redisTemplate) {
        this.keyRepository = keyRepository;
        faker = new Faker();
        this.redisTemplate = redisTemplate;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void mockData() {

        long diff = 10L - keyRepository.count();

        Stream.generate(() -> {
            Key key = new Key();
            key.setKeyValue(faker.shakespeare().asYouLikeItQuote() + faker.random().hex(100));
            key.setIdent(faker.idNumber().valid());
            key.setUseDate(new Date().toInstant());
            keyRedisRepository.save(key);
            return key;
        }).limit(diff).forEach(keyRepository::save);

    }
}

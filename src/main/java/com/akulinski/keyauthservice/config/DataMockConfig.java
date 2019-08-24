package com.akulinski.keyauthservice.config;

import com.akulinski.keyauthservice.core.domain.AppUser;
import com.akulinski.keyauthservice.core.domain.Application;
import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.UserType;
import com.akulinski.keyauthservice.core.repositories.AppUserRepository;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import com.akulinski.keyauthservice.core.repositories.redis.AppUserRedisRepository;
import com.akulinski.keyauthservice.core.repositories.redis.KeyRedisRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Profile("dev")
@Slf4j
public class DataMockConfig {
    private Faker faker;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final KeyRepository keyRepository;

    private final KeyRedisRepository keyRedisRepository;

    private final AppUserRepository appUserRepository;

    private final AppUserRedisRepository appUserRedisRepository;

    public DataMockConfig(KeyRepository keyRepository, RedisTemplate<Object, Object> redisTemplate, KeyRedisRepository keyRedisRepository, AppUserRepository appUserRepository, AppUserRedisRepository appUserRedisRepository) {
        this.keyRepository = keyRepository;
        faker = new Faker();
        this.redisTemplate = redisTemplate;
        this.keyRedisRepository = keyRedisRepository;
        this.appUserRepository = appUserRepository;
        this.appUserRedisRepository = appUserRedisRepository;
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

        diff = 10L - appUserRepository.count();

        Stream.generate(() -> {
            AppUser appUser = new AppUser();
            appUser.setUsername(faker.funnyName().name());
            appUser.setUserType(UserType.BASIC_USER);

            final Set<Application> apps = Stream.generate(() -> {
                Application application = new Application();
                application.setApplicationName(faker.harryPotter().quote());
                application.setAppUsers(Set.of(appUser));
                return application;
            }).limit(faker.random().nextInt(5)).collect(Collectors.toSet());

            appUser.setApplications(apps);
            return appUser;
        }).limit(diff).forEach(appUserRepository::save);

    }
}

package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.dto.KeyDTO;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import com.akulinski.keyauthservice.core.repositories.redis.KeyRedisRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KeyService {

    private final RedisTemplate<String, Key> redisTemplate;

    private final ValidationService validationService;

    private final KeyRepository keyRepository;

    private final KeyRedisRepository keyRedisRepository;

    public KeyService(KeyRepository keyRepository, ValidationService validationService, RedisTemplate redisTemplate, KeyRedisRepository keyRedisRepository) {
        this.keyRepository = keyRepository;
        this.validationService = validationService;
        this.redisTemplate = redisTemplate;
        this.keyRedisRepository = keyRedisRepository;
    }

    public Key addKeyFromDTO(KeyDTO keyDTO) {
        final var byKeyValue = keyRepository.findByKeyValue(keyDTO.getKeyValue());
        if (byKeyValue.isEmpty()) {
            if (validateKey(keyDTO)) {

                Key key = new Key();
                key.setIdent(keyDTO.getIdent());
                key.setKeyValue(keyDTO.getKeyValue());
                key.setIsUsed(Boolean.FALSE);
                key.setUseDate(new Date().toInstant());

                keyRedisRepository.save(key);
                return keyRepository.save(key);
            }
        }
        return null;
    }

    @Cacheable(value = "key")
    public List<Key> findAll() {

        return (List<Key>) keyRedisRepository.findAll();
    }

    public Key findById(String id) {
        Long keyId = Long.parseLong(id);
        return keyRepository.findById(keyId).orElseThrow(() -> new IllegalArgumentException(String.format("Id of: %s Not found", id)));
    }


    private Boolean validateKey(KeyDTO keyDTO) {
        return validationService.validateRegex(keyDTO.getKeyValue());
    }

    public Boolean redeem(KeyDTO keyDTO) {
        final var keyByRedis = keyRedisRepository.findByKeyValue(keyDTO.getKeyValue());

        if (keyByRedis.isPresent()) {
            if (!keyByRedis.get().getIsUsed()) {
                keyByRedis.get().setIsUsed(Boolean.TRUE);
                keyByRedis.get().setUseDate(new Date().toInstant());
                keyRepository.save(keyByRedis.get());
                return Boolean.TRUE;
            }
        } else {
            final var byKeyValue = keyRedisRepository.findByKeyValue(keyDTO.getKeyValue());

            if (byKeyValue.isPresent() && !byKeyValue.get().getIsUsed()) {
                byKeyValue.get().setIsUsed(Boolean.TRUE);
                byKeyValue.get().setUseDate(new Date().toInstant());
                keyRepository.save(byKeyValue.get());
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Cacheable(value = "key", key = "#keyDTO.ident")
    public Boolean validateRequest(KeyDTO keyDTO) {

        final var keyByRedis = keyRedisRepository.findByKeyValue(keyDTO.getKeyValue());


        if (keyByRedis.isPresent()) {
            if (checkKeyFromOptional(keyDTO, keyByRedis)) return Boolean.TRUE;


        } else {
            final var byKeyValue = keyRepository.findByKeyValue(keyDTO.getKeyValue());

            if (byKeyValue.isPresent()) {
                if (checkKeyFromOptional(keyDTO, byKeyValue)) return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private boolean checkKeyFromOptional(KeyDTO keyDTO, Optional<Key> keyByRedis) {
        final var value = keyByRedis.get().getKeyValue();
        final var ident = keyByRedis.get().getIdent();
        final var isUsed = keyByRedis.get().getIsUsed();
        if (keyDTO.getKeyValue().equals(value) && keyDTO.getIdent().equals(ident) && isUsed) {
            return true;
        }
        return false;
    }

}

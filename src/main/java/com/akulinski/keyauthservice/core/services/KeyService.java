package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.KeyDTO;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class KeyService {

    private final ValidationService validationService;

    private final KeyRepository keyRepository;

    public KeyService(KeyRepository keyRepository, ValidationService validationService) {
        this.keyRepository = keyRepository;
        this.validationService = validationService;
    }

    @CachePut(value = "key", key = "#keyDTO.ident")
    public Key addKeyFromDTO(KeyDTO keyDTO) {
        final var byKeyValue = keyRepository.findByKeyValue(keyDTO.getKeyValue());
        if (byKeyValue.isEmpty()) {
            if (validateKey(keyDTO)) {

                Key key = new Key();
                key.setIdent(keyDTO.getIdent());
                key.setKeyValue(keyDTO.getKeyValue());
                key.setIsUsed(Boolean.FALSE);
                key.setUseDate(new Date().toInstant());

                return keyRepository.save(key);
            }
        }
        return null;
    }

    @Cacheable(value = "key")
    public List findAll() {
        return Collections.singletonList(keyRepository.findAll());
    }

    public Key findById(String id) {
        Long keyId = Long.parseLong(id);
        return keyRepository.findById(keyId).orElseThrow(() -> new IllegalArgumentException(String.format("Id of: %s Not found", id)));
    }


    private Boolean validateKey(KeyDTO keyDTO) {
        return validationService.validateRegex(keyDTO.getKeyValue());
    }

    @Cacheable(value = "key", key = "#keyDTO.ident")
    public Boolean redeem(KeyDTO keyDTO) {
        final var byKeyValue = keyRepository.findByKeyValue(keyDTO.getKeyValue());

        if (byKeyValue.isPresent() && !byKeyValue.get().getIsUsed()) {
            byKeyValue.get().setIsUsed(Boolean.TRUE);
            byKeyValue.get().setUseDate(new Date().toInstant());
            keyRepository.save(byKeyValue.get());
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Cacheable(value = "key", key = "#keyDTO.ident")
    public Boolean validateRequest(KeyDTO keyDTO) {
        final var byKeyValue = keyRepository.findByKeyValue(keyDTO.getKeyValue());

        if (byKeyValue.isPresent()) {
            final var value = byKeyValue.get().getKeyValue();
            final var ident = byKeyValue.get().getIdent();
            final var isUsed = byKeyValue.get().getIsUsed();
            if (keyDTO.getKeyValue().equals(value) && keyDTO.getIdent().equals(ident) && isUsed) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

}

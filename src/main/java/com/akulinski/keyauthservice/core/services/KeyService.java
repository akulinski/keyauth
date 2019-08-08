package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.KeyDTO;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
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

    public Key addKeyFromDTO(KeyDTO keyDTO) {

        Key key = new Key();
        key.setIdent(keyDTO.getIdent());
        key.setKeyValue(keyDTO.getKeyValue());
        key.setIsUsed(Boolean.FALSE);
        key.setUseDate(new Date().toInstant());

        return keyRepository.save(key);
    }


    public List findAll() {
        return Collections.singletonList(keyRepository.findAll());
    }

    public Key findById(String id) {
        Long keyId = Long.parseLong(id);
        return keyRepository.findById(keyId).orElseThrow(() -> new IllegalArgumentException(String.format("Id of: %s Not found", id)));
    }


    public Boolean validateKey(KeyDTO keyDTO) {
        return validationService.validateRegex(keyDTO.getKeyValue());
    }

}

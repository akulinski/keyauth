package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.KeyDTO;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import com.akulinski.keyauthservice.core.repositories.redis.KeyRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeyServiceTest {

    @Mock
    private KeyRepository keyRepository;

    @Mock
    private ValidationService validationService;

    private KeyService keyService;

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private KeyRedisRepository keyRedisRepository;

    @BeforeAll
    public void init() {
        keyRepository = mock(keyRepository.getClass());
        validationService = mock(validationService.getClass());
        when(keyRepository.findAll()).thenReturn(Stream.generate(Key::new).limit(10).collect(Collectors.toList()));
        when(keyRepository.save(any(Key.class))).thenReturn(new Key());
        when(keyRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Key()));
        keyService = new KeyService(keyRepository, validationService, redisTemplate, keyRedisRepository);
    }

    @Test
    void addKeyFromDTO() {
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setIdent("aaa");
        keyDTO.setKeyValue("bbb");
        Assertions.assertThat(keyService.addKeyFromDTO(keyDTO) != null);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddKeyFromDTO() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void redeem() {
    }

    @Test
    void validateRequest() {
    }
}

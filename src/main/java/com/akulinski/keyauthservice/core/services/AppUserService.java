package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.AppUser;
import com.akulinski.keyauthservice.core.domain.dto.AddUserDTO;
import com.akulinski.keyauthservice.core.repositories.AppUserRepository;
import com.akulinski.keyauthservice.core.repositories.redis.AppUserRedisRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRedisRepository appUserRedisRepository;

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository, AppUserRedisRepository appUserRedisRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserRedisRepository = appUserRedisRepository;
    }

    /**
     * Checks redis and db for user with name
     *
     * @param username name of user
     * @return user object
     */
    public AppUser getAppUser(String username) {
        final var redisUser = appUserRedisRepository.findByUsername(username);
        return redisUser.orElseGet(() -> appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name: %s found", username))));

    }

    public void addUser(AddUserDTO addUserDTO) {
        final var appUser = appUserRedisRepository.findByUsername(addUserDTO.getUsername())
                .orElseGet(() -> appUserRepository.findByUsername(addUserDTO.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name: %s found", addUserDTO.getUsername()))));


    }
}

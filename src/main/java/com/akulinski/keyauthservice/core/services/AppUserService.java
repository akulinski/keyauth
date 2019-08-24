package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.AppUser;
import com.akulinski.keyauthservice.core.domain.Application;
import com.akulinski.keyauthservice.core.domain.dto.AddUserDTO;
import com.akulinski.keyauthservice.core.repositories.AppUserRepository;
import com.akulinski.keyauthservice.core.repositories.ApplicationRepository;
import com.akulinski.keyauthservice.core.repositories.redis.AppUserRedisRepository;
import com.akulinski.keyauthservice.core.repositories.redis.ApplicationRedisRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRedisRepository appUserRedisRepository;

    private final AppUserRepository appUserRepository;

    private final ApplicationRepository applicationRepository;

    private final ApplicationRedisRepository applicationRedisRepository;

    public AppUserService(AppUserRepository appUserRepository, AppUserRedisRepository appUserRedisRepository, ApplicationRepository applicationRepository, ApplicationRedisRepository applicationRedisRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserRedisRepository = appUserRedisRepository;
        this.applicationRepository = applicationRepository;
        this.applicationRedisRepository = applicationRedisRepository;
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

    @Async
    public void addUser(AddUserDTO addUserDTO) {
        final var appUser = appUserRedisRepository.findByUsername(addUserDTO.getUsername())
                .orElseGet(() -> appUserRepository.findByUsername(addUserDTO.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name: %s found", addUserDTO.getUsername()))));

        final var app = applicationRedisRepository.findByApplicationName(addUserDTO.getApplicationName())
                .orElseGet(() -> applicationRepository.findByApplicationName(addUserDTO.getApplicationName())
                        .orElseThrow(() -> new IllegalArgumentException(String.format("No Application with name: %s", addUserDTO.getApplicationName()))));

        addUserToApp(appUser, app);
    }

    private void addUserToApp(AppUser appUser, Application application) {
        application.getAppUsers().add(appUser);
        appUser.getApplications().add(application);

        applicationRepository.save(application);
        applicationRedisRepository.save(application);
        appUserRedisRepository.save(appUser);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}

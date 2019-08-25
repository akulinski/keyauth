package com.akulinski.keyauthservice.core.services;

import com.akulinski.keyauthservice.core.domain.AppUser;
import com.akulinski.keyauthservice.core.domain.Application;
import com.akulinski.keyauthservice.core.domain.dto.AddUserDTO;
import com.akulinski.keyauthservice.core.repositories.AppUserRepository;
import com.akulinski.keyauthservice.core.repositories.ApplicationRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final ApplicationRepository applicationRepository;


    public AppUserService(AppUserRepository appUserRepository, ApplicationRepository applicationRepository) {
        this.appUserRepository = appUserRepository;
        this.applicationRepository = applicationRepository;
    }

    /**
     * Checks redis and db for user with name
     *
     * @param username name of user
     * @return user object
     */
    public AppUser getAppUser(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name: %s found", username)));

    }

    @Async
    public void addUser(AddUserDTO addUserDTO) {
        final AppUser appUser = appUserRepository.findByUsername(addUserDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name: %s found", addUserDTO.getUsername())));

        final Application app = applicationRepository.findByApplicationName(addUserDTO.getApplicationName())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No Application with name: %s", addUserDTO.getApplicationName())));

        addUserToApp(appUser, app);
    }

    private void addUserToApp(AppUser appUser, Application application) {
        application.getAppUsers().add(appUser);
        appUser.getApplications().add(application);

        applicationRepository.save(application);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
